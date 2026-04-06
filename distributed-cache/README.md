# Distributed Cache

## Class Diagram

```
                    +-----------------------+
                    |   DistributedCache    |
                    +-----------------------+
                    | - nodes: List<CacheNode>         |
                    | - distributionStrategy            |
                    | - database: Database              |
                    +-----------------------+
                    | + get(key): String                 |
                    | + put(key, value): void            |
                    +-----------------------+
                           |         |             |
            uses           |         |  uses       |  uses
                           v         v             v
              +------------------+  +-------------------+  +----------+
              | CacheNode        |  |<<interface>>      |  | Database |
              +------------------+  |DistributionStrategy| +----------+
              | - capacity       |  +-------------------+  | + get()  |
              | - store: Map     |  | + getNode()       |  | + put()  |
              | - evictionPolicy |  +-------------------+  +----------+
              +------------------+         ^
              | + get(key)       |         |
              | + put(key, val)  |  +-------------------+
              +------------------+  |ModuloDistribution |
                     |              |Strategy            |
                uses |              +-------------------+
                     v
              +-------------------+
              |<<interface>>      |
              |EvictionPolicy     |
              +-------------------+
              | + keyAccessed()   |
              | + evict()         |
              | + remove()        |
              +-------------------+
                       ^
                       |
              +-------------------+
              |LRUEvictionPolicy  |
              +-------------------+
              | - accessOrder:    |
              |   LinkedHashMap   |
              +-------------------+
```

## How Data is Distributed Across Nodes

`DistributionStrategy` interface decides which node stores a key. The current implementation uses `ModuloDistributionStrategy` which computes `Math.abs(key.hashCode()) % totalNodes`. This gives a deterministic node index for any key. To switch to consistent hashing later, just implement `DistributionStrategy` with a new class.

## How Cache Miss is Handled

In `DistributedCache.get(key)`:
1. Determine the node using the distribution strategy
2. Try to get the value from that node
3. If null (cache miss), fetch from `Database`
4. If found in DB, store it in the cache node for future hits
5. Return the value (or null if not in DB either)

## How Eviction Works

Each `CacheNode` has a capacity. When a `put()` would exceed capacity:
1. The `EvictionPolicy.evict()` is called to get the least recently used key
2. That key is removed from the node's store
3. The new key-value is then inserted

`LRUEvictionPolicy` uses `LinkedHashMap` with access-order mode. The first entry in iteration order is the least recently used.

## How the Design Supports Extensibility

- **New eviction policies**: Implement `EvictionPolicy` (e.g., MRU, LFU) and pass it during cache construction
- **New distribution strategies**: Implement `DistributionStrategy` (e.g., ConsistentHashing) and inject it into `DistributedCache`
- Number of nodes and capacity per node are configurable via constructor

## How to Run

```bash
cd distributed-cache
mkdir -p out
javac -d out src/com/example/cache/*.java
java -cp out com.example.cache.App
```
