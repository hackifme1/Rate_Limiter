import algo.impl.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        TokenBucket tokenBucket = new TokenBucket(5, 1);
//        LeakBucket leakBucket = new LeakBucket(5, 1);
//        FixedWindowCounter fixedWindowCounter = new FixedWindowCounter(10, 1000);
//        SlidingWindowLog slidingWindowLog = new SlidingWindowLog(10, 1000);

        SlidingWindowCounter slidingWindowCounter = new SlidingWindowCounter(10, 1000);
        for (int i = 0; i < 100; i++) {
            boolean allowed = slidingWindowCounter.tryAcquire();
            System.out.println("Request " + i + ": " + (allowed ? "Allowed" : "Denied"));
            Thread.sleep(50); // Simulate time between requests
        }
    }
}