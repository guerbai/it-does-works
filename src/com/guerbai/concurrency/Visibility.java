import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Visibility {

    // if declare without `volatile`, this testcase will not end, 
    // cause CountTask thread can't see running modified by SleepTask.
    // You can change this and have a try, feel the obvious difference.

    // boolean running = true;
    volatile boolean running = true;

    class SleepTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                // catch block
            }
            System.out.println("Thread 2 finishing");
            running = false;
        }
    }

    class CountTask implements Runnable {

        @Override
        public void run() {
            int counter = 0;
            while (running) {
                counter++;
            }
            System.out.println("Thread 1 finished. Counted up to " + counter);
        }
    }


    private void testVisibility() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new CountTask());
        executor.submit(new SleepTask());
        executor.shutdown();
    }

    public static void main(String[] args) {
        new Visibility().testVisibility();

    }

}