package com.example.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheNode {
    private int capacity;
    private Map<String, String> store;
    private EvictionPolicy evictionPolicy;

    public CacheNode(int capacity, EvictionPolicy evictionPolicy) {
        this.capacity = capacity;
        this.store = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String get(String key) {
        if (store.containsKey(key)) {
            evictionPolicy.keyAccessed(key);
            return store.get(key);
        }
        return null;
    }

    public void put(String key, String value) {
        if (store.containsKey(key)) {
            store.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }
        if (store.size() >= capacity) {
            String evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                store.remove(evictedKey);
            }
        }
        store.put(key, value);
        evictionPolicy.keyAccessed(key);
    }
}
