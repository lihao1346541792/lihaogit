package com.qqtech.core.frame.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.qqtech.core.frame.dao.BaseDao;
import com.qqtech.core.frame.model.ABaseable;
import com.qqtech.core.frame.model.PageOrder;

public abstract class BaseServiceImpl<T extends ABaseable> implements BaseService<T> {
	protected abstract BaseDao<T> getBaseDao();

	@Override
	public <V extends T> V getOne(T query, PageOrder... pageOrders) {
		return getBaseDao().getOne(query, pageOrders);
	}

	@Override
	public <V extends T> V getById(Integer id) {
		return getBaseDao().getById(id);
	}

	@Override
	public <V extends T> List<V> queryList(T query) {
		return getBaseDao().queryList(query);
	}

	@Override
	public <V extends T> List<V> queryAll() {
		return getBaseDao().queryAll();
	}

	@Override
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey) {
		return getBaseDao().queryMap(query, mapKey);
	}

	@Override
	public int queryCount() {
		return getBaseDao().queryCount();
	}

	@Override
	public int queryCount(T query) {
		return getBaseDao().queryCount(query);
	}

	@Override
	public int insert(T entity) {
		return getBaseDao().insert(entity);
	}

	@Override
	public int delete(T query) {
		return getBaseDao().delete(query);
	}

	@Override
	public int deleteById(Integer id) {
		return getBaseDao().deleteById(id);
	}

	@Override
	public int deleteAll() {
		return getBaseDao().deleteAll();
	}

	@Override
	public int updateById(T entity) {
		return getBaseDao().updateById(entity);
	}

	@Override
	public void deleteByIdInBatch(List<Integer> idList) {
		getBaseDao().deleteByIdInBatch(idList);
	}

	@Override
	public void insertInBatch(List<T> entityList) {
		getBaseDao().insertInBatch(entityList);
	}

	@Override
	public void updateInBatch(List<T> entityList) {
		getBaseDao().updateInBatch(entityList);
	}

	@Override
	public <V extends T> List<V> queryList(T query, Pageable pageable) {
		return getBaseDao().queryList(query, pageable);
	}

	@Override
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable) {
		return getBaseDao().queryPageList(query, pageable);
	}

	@Override
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey, Pageable pageable) {
		return getBaseDao().queryMap(query, mapKey, pageable);
	}

}
