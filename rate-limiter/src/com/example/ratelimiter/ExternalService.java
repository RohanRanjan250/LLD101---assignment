package com.example.ratelimiter;

public class ExternalService {
    private RateLimiter rateLimiter;

    public ExternalService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public String callExternalResource(String key) {
        if (!rateLimiter.allowRequest(key)) {
            return "RATE_LIMITED: Request denied for key=" + key;
        }
        return "SUCCESS: External call made for key=" + key;
    }
}
