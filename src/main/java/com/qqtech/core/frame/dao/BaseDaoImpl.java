package com.qqtech.core.frame.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.qqtech.core.common.util.BeanUtil;
import com.qqtech.core.frame.model.ABaseable;
import com.qqtech.core.frame.model.PageKit;
import com.qqtech.core.frame.model.PageOrder;

public abstract class BaseDaoImpl<T extends ABaseable> implements BaseDao<T> {
	@Resource
	protected SqlSession sqlSessionTemplate;

	public static final String SQLNAME_SEPARATOR = ".";
	private String sqlNamespace = getDefaultSqlNamespace();

	@Override
	public <V extends T> V getById(Integer id) {
		return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_BY_ID), id);
	}

	@Override
	public <V extends T> V getOne(T query, PageOrder... pageOrders) {
		Pageable page = new PageKit();
		if (ArrayUtils.isNotEmpty(pageOrders)) {
			page = new PageKit(pageOrders[0]);
		}
		return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT), getParams(query, page));
	}

	@Override
	public <V extends T> List<V> queryList(T query) {
		return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), BeanUtil.toMap(query));
	}

	@Override
	public <V extends T> List<V> queryAll() {
		return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT));
	}

	@Override
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey) {
		return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), BeanUtil.toMap(query), mapKey);
	}

	@Override
	public <V extends T> List<V> queryList(T query, Pageable pageable) {
		return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
	}

	@Override
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable) {
		List<V> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
		return new PageImpl<V>(contentList, pageable, this.queryCount(query));
	}

	@Override
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Pageable pageable) {
		return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable), mapKey);
	}

	@Override
	public int queryCount() {
		Integer t = sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT));
		return t == null ? 0 : t;
	}

	@Override
	public int queryCount(T query) {
		Integer t = sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), BeanUtil.toMap(query));
		return t == null ? 0 : t;
	}

	@Override
	public int insert(T entity) {
		int id = sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), entity);
		if (entity.getId() == null || entity.getId().intValue() == 0) {
			entity.setId(id);
		} else {
			id = entity.getId();
		}
		return id;
	}

	@Override
	public int delete(T query) {
		return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE), BeanUtil.toMap(query));
	}

	@Override
	public int deleteById(Integer id) {
		return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE_BY_ID), id);
	}

	@Override
	public int deleteAll() {
		return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE));
	}

	@Override
	public int updateById(T entity) {
		if (entity.getId() == null || entity.getId().intValue() == 0) {
			return 0;
		}
		return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);
	}

	@Override
	public void deleteByIdInBatch(List<Integer> idList) {
		if (idList != null && idList.size() > 0) {
			for (Integer id : idList) {
				this.deleteById(id);
			}
		}
	}

	@Override
	public void updateInBatch(List<T> entityList) {
		if (entityList != null && entityList.size() > 0) {
			for (T entity : entityList) {
				this.updateById(entity);
			}
		}

	}

	@Override
	public int insertInBatch(List<T> entityList) {
		if (entityList == null || entityList.size() == 0) {
			return 0;
		}
		return sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERTBATCH), entityList);
	}

	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtil.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}

	public String getSqlNamespace() {
		return sqlNamespace;
	}

	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}

	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}

	protected RowBounds getRowBounds(Pageable pageable) {
		RowBounds bounds = RowBounds.DEFAULT;
		if (null != pageable) {
			bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
		}
		return bounds;
	}

	protected Map<String, Object> getParams(T query, Pageable pageable) {
		Map<String, Object> params = BeanUtil.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
		}
		return params;
	}
}
