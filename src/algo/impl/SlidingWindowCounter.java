package algo.impl;

import algo.RateLimiter;

public class SlidingWindowCounter implements RateLimiter {

    private final long windowSizeMillis;
    private final long maxRequests;

    private long windowStartTime;
    private int currentCount;
    private int previousCount;

    public SlidingWindowCounter(long maxRequests, long windowSizeMillis) {
        this.windowSizeMillis = windowSizeMillis;
        this.maxRequests = maxRequests;
        this.windowStartTime = System.currentTimeMillis();
        this.currentCount = 0;
        this.previousCount = 0;
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        long elapsed = now - windowStartTime;

        if (elapsed >= windowSizeMillis) {
            // Move window forward
            previousCount = currentCount;
            currentCount = 0;
            windowStartTime = now;
            elapsed = 0; // reset for accurate weight calculation
        }

        double weight = 1 - (elapsed / (double) windowSizeMillis);
        double estimatedRequests = previousCount * weight + currentCount;

        if (estimatedRequests < maxRequests) {
            currentCount++;
            return true;
        }

        return false;
    }
}
