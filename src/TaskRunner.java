import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.*;

public class TaskRunner {

	public static void main(String[] args) {

		int precision = 1500;
		int tasks = 1;
		boolean quiet = false;
		String file_name = "result.txt";
		
		try {
			for (int i = 0; i< args.length; i++){
				switch(args[i]){
				case "-p": precision = Integer.parseInt(args[i+1]);
					break;
				case "-t": case "-tasks": tasks = Integer.parseInt(args[i+1]);
					break;
				case "-q": quiet = true;
					break;
				case "-o": file_name = args[i+1];
					break;
				}
			}
		} catch(RuntimeException e){
			System.out.println("Invalid arguments.");
			System.exit(1);
		}
		
		long startTime = System.currentTimeMillis();
		
		int p_digits = precision;
		int thread_count = tasks;
		int chunk_size = p_digits / thread_count;
		
		BigDecimal res[] = new BigDecimal[thread_count];
		Thread tr[] = new Thread[thread_count];
		
		for(int i = 0; i < thread_count; i++) {
			Napier r = new Napier(res, chunk_size, i, thread_count, quiet);
			Thread t = new Thread(r);
			tr[i] = t;
			t.start();
		}
		
		BigDecimal sum = BigDecimal.valueOf(0);
		
		for(int i = 0; i < thread_count; i++) {
			try {
				tr[i].join();
				sum = sum.add(res[i]);
			} catch (InterruptedException e) {
				
			}
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		
		if (!quiet){
			System.out.println("Threads used in current run: " + thread_count);
		}
		
		try (PrintStream file = new PrintStream(file_name)) {
			file.println(sum);
		} catch (FileNotFoundException fnf) {
			System.out.println("File " + file_name + " not found.");
		}
		
		System.out.println("Total execution time for current run: " + elapsedTime + "ms");
	}
}
