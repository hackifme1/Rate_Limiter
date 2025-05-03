package algo.impl;

import algo.RateLimiter;

public class TokenBucket implements RateLimiter {

    private final long capacity; // Max number of tokens (threshold)
    private final long fillRate; // Tokens added per second

    private long lastRefillTimeStamp; // Last refill time in milliseconds
    private long currentTokens; // Current available tokens

    public TokenBucket(long capacity, long fillRate) {
        this.capacity = capacity;
        this.fillRate = fillRate;
        this.currentTokens = capacity;
        this.lastRefillTimeStamp = System.currentTimeMillis();
    }

    // in multithreaded environment, making thread safe the critical section
    // however, in case of distributed system will be requiring Redis(global cache)
    @Override
    public synchronized boolean tryAcquire() {

        refill();

        if(currentTokens > 0) {
            currentTokens--;
            return true; // Access granted
        }

        return false; // Access denied
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsedMillis  = now - lastRefillTimeStamp;

        long tokensToAdd = (elapsedMillis / 1000) * fillRate;

        if(tokensToAdd > 0) {
            currentTokens = Math.min(capacity, currentTokens + tokensToAdd); // to ensure tokens remain well within capacity
            lastRefillTimeStamp = now;
        }
    }
}