package anagram;

/**
 * Created by thoma on 21-May-17.
 */
public class Benchmark {
	public static void test_ns(Runnable runnable){
		long startTime = System.nanoTime();
		runnable.run();
		long endTime = System.nanoTime();
		System.out.println("Total execution time: " + (endTime-startTime) + "ns");
	}
	public static void test_ms(Runnable runnable){
		long startTime = System.currentTimeMillis();
		runnable.run();
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime-startTime) + "ms");
	}
}
