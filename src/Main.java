import algo.impl.FixedWindowCounter;
import algo.impl.LeakBucket;
import algo.impl.TokenBucket;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        TokenBucket tokenBucket = new TokenBucket(5, 1);
//        LeakBucket leakBucket = new LeakBucket(5, 1);
        FixedWindowCounter fixedWindowCounter = new FixedWindowCounter(10, 1000);
        for (int i = 0; i < 100; i++) {
            boolean allowed = fixedWindowCounter.tryAcquire();
            System.out.println("Request " + i + ": " + (allowed ? "Allowed" : "Denied"));
            Thread.sleep(50); // Simulate time between requests
        }
    }
}