import java.math.*;

public class TaskRunner {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		if (args.length != 2) {
			System.out.println("TaskRunner <num of elements> <num of threads>");
			System.exit(1);
		}
		
		int p_digits = new Integer(args[0]);
		int thread_count = new Integer(args[1]);
		
		int chunk_size = p_digits / thread_count;

		//double a[] = new double[p_digits];
		BigDecimal a[] = new BigDecimal[p_digits];
		BigDecimal res[] = new BigDecimal[thread_count];
	
		Thread tr[] = new Thread[thread_count];
		
		for(int i = 0; i < thread_count; i++) {

			Napier r = new Napier(a, res, chunk_size, i, thread_count);
			Thread t = new Thread(r);
			tr[i] = t;
			t.start();
		
		}
		
		BigDecimal sum = BigDecimal.valueOf(0);
		// BigInteger sum = BigInteger.valueOf(1);
		for(int i = 0; i < thread_count; i++) {
			try {
				tr[i].join();
				sum = sum.add(res[i]);
				// sum = sum.multiply(res[i]);
			} catch (InterruptedException e) {
				
			}
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("result: " + sum);
		System.out.println(elapsedTime + "ms");
		System.out.println(Runtime.getRuntime().availableProcessors());
		
	}

}
