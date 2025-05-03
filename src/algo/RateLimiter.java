package algo;

public interface RateLimiter {

    boolean tryAcquire();
}