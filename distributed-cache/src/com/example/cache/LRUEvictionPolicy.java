package com.example.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;

public class LRUEvictionPolicy implements EvictionPolicy {
    private LinkedHashMap<String, Boolean> accessOrder;

    public LRUEvictionPolicy() {
        this.accessOrder = new LinkedHashMap<>(16, 0.75f, true);
    }

    @Override
    public void keyAccessed(String key) {
        accessOrder.put(key, true);
    }

    @Override
    public String evict() {
        Iterator<Map.Entry<String, Boolean>> it = accessOrder.entrySet().iterator();
        if (it.hasNext()) {
            String key = it.next().getKey();
            it.remove();
            return key;
        }
        return null;
    }

    @Override
    public void remove(String key) {
        accessOrder.remove(key);
    }
}
