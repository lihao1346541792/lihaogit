package com.qqtech.core.frame.model;

/**
 * 缓存接口，K是缓存的key,V是缓存的value
 * 
 * @author andy
 * 
 *         2015-6-30
 */
public interface QquanCache<K, V> {
	V get(K k);

	void clear(K k);

	void clearAll();
}
