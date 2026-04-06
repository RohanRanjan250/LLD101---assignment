package com.example.cache;

import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private List<CacheNode> nodes;
    private DistributionStrategy distributionStrategy;
    private Database database;

    public DistributedCache(int numberOfNodes, int capacityPerNode, DistributionStrategy distributionStrategy, EvictionPolicy evictionPolicyTemplate, Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode(capacityPerNode, createEvictionPolicy(evictionPolicyTemplate)));
        }
    }

    private EvictionPolicy createEvictionPolicy(EvictionPolicy template) {
        if (template instanceof LRUEvictionPolicy) {
            return new LRUEvictionPolicy();
        }
        return new LRUEvictionPolicy();
    }

    public String get(String key) {
        int nodeIndex = distributionStrategy.getNode(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        String value = node.get(key);
        if (value == null) {
            value = database.get(key);
            if (value != null) {
                node.put(key, value);
            }
        }
        return value;
    }

    public void put(String key, String value) {
        int nodeIndex = distributionStrategy.getNode(key, nodes.size());
        nodes.get(nodeIndex).put(key, value);
        database.put(key, value);
    }
}
