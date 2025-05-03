package algo.impl;

import algo.RateLimiter;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowLog implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;
    private final Deque<Long> requestLogs;

    public SlidingWindowLog(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.requestLogs = new ArrayDeque<>();
    }

    @Override
    public synchronized boolean tryAcquire() {

        long currentTime = System.currentTimeMillis();
        checkLog(currentTime);

        if (requestLogs.size() < maxRequests) {
            requestLogs.addLast(currentTime);
            return true;
        }

        return false;
    }

    private void checkLog(long currentTime) {

        // Remove expired timestamps
        while (!requestLogs.isEmpty() && requestLogs.peekFirst() <= (currentTime - windowSizeMillis)) {
            requestLogs.pollFirst();
        }
    }
}
