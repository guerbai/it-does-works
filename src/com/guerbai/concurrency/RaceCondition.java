import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.nio.channels.*;


class RaceCondition {
	
	private long num = 0;
    
    // Use synchronized count, this code always print 20000 after two countTask.
    // Then you can try with below commented line without synchronized, 
    // race condition make it difficult to figure out what num will be after code end.
    // private void count() {
	private synchronized void count() {
		num++;
	}
	
	class CountTask implements Runnable {

		@Override
		public void run() {
			for (int i=0; i<10000; i++) {
				count();
			}
		}
	}
	
	private void testRaceCondition() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountTask ct1 = new CountTask();
        CountTask ct2 = new CountTask();
		executor.submit(ct1);
		executor.submit(ct2);
        executor.shutdown();
        // use awaitTermination wait all task finish.
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		RaceCondition rc = new RaceCondition();
		rc.testRaceCondition();
		System.out.println(rc.num);
    }

}