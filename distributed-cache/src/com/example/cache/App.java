package com.example.cache;

public class App {
    public static void main(String[] args) {
        Database db = new Database();
        db.put("user1", "Alice");
        db.put("user2", "Bob");
        db.put("user3", "Charlie");

        DistributionStrategy strategy = new ModuloDistributionStrategy();
        EvictionPolicy evictionPolicy = new LRUEvictionPolicy();

        DistributedCache cache = new DistributedCache(3, 2, strategy, evictionPolicy, db);

        cache.put("key1", "value1");
        cache.put("key2", "value2");

        System.out.println("get(key1): " + cache.get("key1"));
        System.out.println("get(key2): " + cache.get("key2"));

        System.out.println("get(user1) [cache miss, fetched from db]: " + cache.get("user1"));
        System.out.println("get(user1) [should be cached now]: " + cache.get("user1"));

        System.out.println("get(user2) [cache miss]: " + cache.get("user2"));
        System.out.println("get(user3) [cache miss]: " + cache.get("user3"));

        System.out.println("get(nonexistent): " + cache.get("nonexistent"));
    }
}
