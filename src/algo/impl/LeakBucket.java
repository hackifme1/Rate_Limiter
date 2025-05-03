package algo.impl;

import algo.RateLimiter;

public class LeakBucket implements RateLimiter {

    private final long capacity; // max token (queue size)
    private final long leakRate; // Tokens added per second
    private long lastCheckedTime; // Last leaked time in milliseconds
    private long currentTokens; // Current available tokens

    public LeakBucket(long capacity, long leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.lastCheckedTime = System.currentTimeMillis();
        this.currentTokens = 0;
    }

    // in multithreaded environment, making thread safe the critical section
    // however, in case of distributed system will be requiring Redis(global cache)
    @Override
    public synchronized boolean tryAcquire() {

        check();

        if(currentTokens < capacity) {
            currentTokens++;
            return true; // Access granted
        }

        return false; // Access denied
    }

    private void check() {
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastCheckedTime;
        long leakedTokens = (elapsedTime/1000) * leakRate;

        if(leakedTokens > 0) {
            currentTokens = Math.max(currentTokens - leakedTokens, 0); // to ensure non-negative value
            lastCheckedTime = now;
        }
    }
}
