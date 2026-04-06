package com.example.cache;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> store;

    public Database() {
        this.store = new HashMap<>();
    }

    public String get(String key) {
        return store.get(key);
    }

    public void put(String key, String value) {
        store.put(key, value);
    }
}
