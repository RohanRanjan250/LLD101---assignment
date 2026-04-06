package com.example.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounter implements RateLimiter {
    private int maxRequests;
    private long windowSizeMs;
    private Map<String, long[]> windows;

    public SlidingWindowCounter(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.windows = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        long[] data = windows.get(key);

        if (data == null) {
            data = new long[]{now, 1, 0};
            windows.put(key, data);
            return true;
        }

        long windowStart = data[0];
        long currentCount = data[1];
        long previousCount = data[2];

        if (now - windowStart >= windowSizeMs) {
            data[2] = currentCount;
            data[0] = windowStart + windowSizeMs;
            data[1] = 1;
            return true;
        }

        double elapsedRatio = (double)(now - windowStart) / windowSizeMs;
        double weightedCount = previousCount * (1 - elapsedRatio) + currentCount;

        if (weightedCount < maxRequests) {
            data[1] = currentCount + 1;
            return true;
        }

        return false;
    }
}
