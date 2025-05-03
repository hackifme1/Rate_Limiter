import algo.impl.LeakBucket;
import algo.impl.TokenBucket;

public class Main {
    public static void main(String[] args) throws InterruptedException {

//        TokenBucket tokenBucket = new TokenBucket(5, 1);
        LeakBucket leakBucket = new LeakBucket(5, 1);

        for (int i = 0; i < 100; i++) {
            boolean allowed = leakBucket.tryAcquire();
            System.out.println("Request " + i + ": " + (allowed ? "Allowed" : "Denied"));
            Thread.sleep(300); // Simulate time between requests
        }
    }
}