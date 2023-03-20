package com.zhou.stream.store;

import java.util.HashMap;
import java.util.Map;

/**
 * this a store that define max capacity
 * Data beyond this capacity will be persisted by some strategy;
 *
 * @author 周俊宇
 */
public class ZStore<K, V> {

	/**
	 * TODO it can more fast when this store will be optimized;
	 */
	private Map<K, ZV<V>> dataStore;

	private Map<K, V> willAbandonDataStore;

	private int currentCapacity = 0;

	private int maxCapacity;

	private Strategy dataStrategy;

	/**
	 * In fact use 1.5 times memory that  set capacity number
	 * but you data number more than 1.5 times capacity,
	 * this system will use many I\O to keep system running.
	 * it will become very slow until the next minute;
	 *
	 * @param maxCapacity
	 */
	public ZStore(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		dataStore = new HashMap<K, ZV<V>>(maxCapacity);
		willAbandonDataStore = new HashMap<K, V>(maxCapacity / 2);
	}


	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key, the old
	 * value is replaced.
	 * that's not thread safe
	 *
	 * @param k
	 * @param v
	 */
	public void put(K k, V v) {
		if (!dataStore.containsKey(k)) {
			if (currentCapacity == maxCapacity) {
				switch (dataStrategy) {
					case LRU:
						Lru();
						break;
					case DISCARD:
					default:
						return;
				}
			}
		}
	}

	/**
	 * Lru arithmetic
	 */
	private void Lru() {

	}

	private void clean() {
		dataStore.clear();
		willAbandonDataStore.clear();
	}

	enum Strategy {
		FIFO, LRU, WEIGHT, DISCARD;
	}

	/**
	 * a inner data struct for some strategy
	 *
	 * @param <V>
	 */
	static class ZV<V> {
		V v;
		int count = 0;
	}
}
