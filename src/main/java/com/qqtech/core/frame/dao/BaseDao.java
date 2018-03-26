package com.qqtech.core.frame.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.qqtech.core.frame.model.ABaseable;
import com.qqtech.core.frame.model.PageOrder;

public interface BaseDao<T extends ABaseable> {

	/**
	 * 通过Id查询一个对象
	 * 
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(Integer id);

	/**
	 * 查询一个对象
	 * 
	 * @param query
	 * @param pageOrders
	 * @return
	 */
	public <V extends T> V getOne(T query, PageOrder... pageOrders);

	/**
	 * 查询对象列表
	 * 
	 * @param query
	 * @return
	 */
	public <V extends T> List<V> queryList(T query);

	/**
	 * 查询所有记录列表
	 * 
	 * @return
	 */
	public <V extends T> List<V> queryAll();

	/**
	 * 根据结果集中的一列作为key，将结果集转换成Map
	 * 
	 * @param <K>
	 *            返回Map的key类型
	 * @param <V>
	 *            返回Map的Value类型
	 * @param query
	 *            查询参数,如果未null则查询所有对象
	 * @param mapKey
	 *            返回结果List中‘mapKey’属性值作为Key (The property to use as key for each
	 *            value in the list.)
	 * @return Map 包含key属性值的Map对象
	 */
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey);

	/**
	 * <pre>
	 * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
	 * </pre>
	 * 
	 * @param query
	 *            查询参数
	 * @param pageable
	 *            分页对象
	 * @return List 根据分页对象查询的分页结果列表
	 */
	public <V extends T> List<V> queryList(T query, Pageable pageable);

	/**
	 * <pre>
	 * 查询对象列表，注意：在给定非null的分页对象时该方法自动设置分页总记录数,如果query和pageable同时为null则查询所有
	 * </pre>
	 * 
	 * @param query
	 *            查询参数
	 * @param pageInfo
	 *            分页对象
	 * @return Page 信息方便前台显示
	 */
	public <V extends T> Page<V> queryPageList(T query, Pageable pageable);

	/**
	 * 根据结果集中的一列作为key，将结果集转换成Map
	 * 
	 * @param <K>
	 *            返回Map的key类型
	 * @param <V>
	 *            返回Map的Value类型
	 * @param query
	 *            查询参数
	 * @param mapKey
	 *            返回结果List中‘mapKey’属性值作为Key (The property to use as key for each
	 *            value in the list.)
	 * @param page
	 *            分页对象
	 * @return Map containing key pair data.
	 */
	public <K, V extends T> Map<K, V> queryMap(T query, String mapKey,
			Pageable pageable);

	/**
	 * 查询总记录数
	 * 
	 * @return int 记录总数
	 */
	public int queryCount();

	/**
	 * 查询记录数
	 * 
	 * @param query
	 *            查询对象，如果为null，则查询对象总数
	 * @return int 记录总数
	 */
	public int queryCount(T query);

	/**
	 * 插入记录
	 * 
	 * @param entity
	 * @return 该记录的主键
	 */
	public int insert(T entity);

	/**
	 * 删除对象
	 * 
	 * @param entity
	 *            要删除的实体对象，不能为null
	 * @return int 受影响结果数
	 */
	public int delete(T query);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 *            要删除的ID，不能为null
	 * @return int 受影响结果数
	 */
	public int deleteById(Integer id);

	/**
	 * 删除所有
	 * 
	 * @return int 受影响结果数
	 */
	public int deleteAll();

	/**
	 * 更新对象，对象必须设置ID
	 * 
	 * @param entity
	 *            实体的Id不能为null
	 * @return int 受影响结果数
	 */
	public int updateById(T entity);

	/**
	 * 根据id，批量删除记录，如果传入的列表为null或为空列表则直接返回
	 * 
	 * @param idList
	 *            批量删除ID列表
	 */
	public void deleteByIdInBatch(List<Integer> idList);

	/**
	 * 批量插入，如果为空列表则直接返回
	 * 
	 * @param entityList
	 *            需要批量插入的实体对象列表
	 * @return 
	 */
	public int insertInBatch(List<T> entityList);

	/**
	 * 批量更新，该方法根据实体ID更新已设置的字段，未设置的字段不更新
	 * 
	 * @param entityList
	 *            批量更新的实体对象列表
	 */
	public void updateInBatch(List<T> entityList);

}
