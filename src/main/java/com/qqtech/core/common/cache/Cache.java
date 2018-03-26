package com.qqtech.core.common.cache;

public interface Cache<K, V> {
	V get(K k);

	void clear(K k);

	void clearAll();
}
