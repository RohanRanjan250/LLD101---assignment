package com.example.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowCounter implements RateLimiter {
    private int maxRequests;
    private long windowSizeMs;
    private Map<String, long[]> windows;

    public FixedWindowCounter(int maxRequests, long windowSizeMs) {
        this.maxRequests = maxRequests;
        this.windowSizeMs = windowSizeMs;
        this.windows = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        long[] data = windows.get(key);

        if (data == null) {
            data = new long[]{now, 1};
            windows.put(key, data);
            return true;
        }

        long windowStart = data[0];
        long count = data[1];

        if (now - windowStart >= windowSizeMs) {
            data[0] = now;
            data[1] = 1;
            return true;
        }

        if (count < maxRequests) {
            data[1] = count + 1;
            return true;
        }

        return false;
    }
}
