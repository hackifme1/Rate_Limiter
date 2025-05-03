package algo.impl;

import algo.RateLimiter;

public class FixedWindowCounter implements RateLimiter {

    private final long maxRequests;
    private final long windowSizeMillis;

    private long requestCount;
    private long windowStart;

    public FixedWindowCounter(long maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.requestCount = 0;
        this.windowStart = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean tryAcquire() {

        windowCheck();

        if(requestCount < maxRequests) {

            requestCount++;
            return true;
        }

        return false;
    }

    private void windowCheck() {

        long now = System.currentTimeMillis();

        if ((now - windowStart) >= windowSizeMillis) {

            requestCount = 0;
            windowStart = now;
        }
    }
}
