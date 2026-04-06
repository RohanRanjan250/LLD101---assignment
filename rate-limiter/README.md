# Rate Limiter

## Class Diagram

```
              +-------------------+
              |<<interface>>      |
              |RateLimiter        |
              +-------------------+
              | + allowRequest(key)|
              +-------------------+
                 ^             ^
                 |             |
    +--------------------+  +------------------------+
    |FixedWindowCounter  |  |SlidingWindowCounter    |
    +--------------------+  +------------------------+
    | - maxRequests      |  | - maxRequests          |
    | - windowSizeMs     |  | - windowSizeMs         |
    | - windows: Map     |  | - windows: Map         |
    +--------------------+  +------------------------+
    | + allowRequest()   |  | + allowRequest()       |
    +--------------------+  +------------------------+

              +-------------------+
              |RateLimiterFactory |
              +-------------------+
              | + create(algo,    |
              |   maxReq, window) |
              +-------------------+

              +-------------------+
              |ExternalService    |
              +-------------------+
              | - rateLimiter     |
              +-------------------+
              | + callExternal    |
              |   Resource(key)   |
              +-------------------+
```

## How It Works

`ExternalService` holds a `RateLimiter`. Before calling the external resource, it checks `rateLimiter.allowRequest(key)`. If denied, the request is rejected. The rate limiting key can be a tenant ID, customer ID, API key, etc.

### Fixed Window Counter
- Divides time into fixed windows (e.g., 1 minute)
- Counts requests per key within each window
- Resets count when a new window starts
- **Trade-off**: Simple but can allow burst at window boundaries (up to 2x limit if requests happen right before and after a window reset)

### Sliding Window Counter
- Tracks current and previous window counts
- Weighted count = `previousCount * (1 - elapsedRatio) + currentCount`
- Smoother rate limiting than fixed window
- **Trade-off**: Slightly more complex, approximation-based, but avoids the boundary burst problem

## Switching Algorithms

```java
// Fixed window
RateLimiter limiter = RateLimiterFactory.create(Algorithm.FIXED_WINDOW, 100, 60000);

// Switch to sliding window - no change in ExternalService
RateLimiter limiter = RateLimiterFactory.create(Algorithm.SLIDING_WINDOW, 100, 60000);
```

The `ExternalService` depends on the `RateLimiter` interface, so swapping algorithms requires no changes in business logic.

## Thread Safety

Both implementations use `synchronized` on `allowRequest()` and `ConcurrentHashMap` for the backing store.

## Extensibility

To add Token Bucket or Leaky Bucket:
1. Implement `RateLimiter` interface
2. Add enum entry in `RateLimiterFactory`
3. No changes needed in `ExternalService`

## How to Run

```bash
cd rate-limiter
mkdir -p out
javac -d out src/com/example/ratelimiter/*.java
java -cp out com.example.ratelimiter.App
```
