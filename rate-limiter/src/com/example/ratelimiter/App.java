package com.example.ratelimiter;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Fixed Window Counter (5 requests per minute) ===");
        RateLimiter fixedWindow = RateLimiterFactory.create(
            RateLimiterFactory.Algorithm.FIXED_WINDOW, 5, 60000
        );
        ExternalService service1 = new ExternalService(fixedWindow);

        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + ": " + service1.callExternalResource("tenant-T1"));
        }

        System.out.println();
        System.out.println("=== Sliding Window Counter (5 requests per minute) ===");
        RateLimiter slidingWindow = RateLimiterFactory.create(
            RateLimiterFactory.Algorithm.SLIDING_WINDOW, 5, 60000
        );
        ExternalService service2 = new ExternalService(slidingWindow);

        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + ": " + service2.callExternalResource("tenant-T1"));
        }

        System.out.println();
        System.out.println("=== Multiple Keys ===");
        RateLimiter multiKeyLimiter = RateLimiterFactory.create(
            RateLimiterFactory.Algorithm.FIXED_WINDOW, 2, 60000
        );
        ExternalService service3 = new ExternalService(multiKeyLimiter);

        System.out.println("T1 req1: " + service3.callExternalResource("T1"));
        System.out.println("T2 req1: " + service3.callExternalResource("T2"));
        System.out.println("T1 req2: " + service3.callExternalResource("T1"));
        System.out.println("T1 req3: " + service3.callExternalResource("T1"));
        System.out.println("T2 req2: " + service3.callExternalResource("T2"));
        System.out.println("T2 req3: " + service3.callExternalResource("T2"));
    }
}
