package com.example.ratelimiter;

public class RateLimiterFactory {
    public enum Algorithm {
        FIXED_WINDOW,
        SLIDING_WINDOW
    }

    public static RateLimiter create(Algorithm algorithm, int maxRequests, long windowSizeMs) {
        switch (algorithm) {
            case FIXED_WINDOW:
                return new FixedWindowCounter(maxRequests, windowSizeMs);
            case SLIDING_WINDOW:
                return new SlidingWindowCounter(maxRequests, windowSizeMs);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }
}
