import java.math.*;

public class TaskRunner {

	public static void main(String[] args) {
		/* 	Програмата извежда подходящи съобщения на различните етапи от работата си, както и 
			времето отделено за изчисление и резултата от изчислението (стойността на  e);
			Примери за подходящи съобщения:
			„Thread-<num> started.“,„Thread-<num> stopped.“,„Thread-<num> execution time was (millis): <num>“,
			„Threads used in current run: <num>“,„Total execution time for current run (millis): <num>“ и т.н.;
			(o)  Записва  резултата  от  работа  си  (стойността  на e) във изходен файл, зададен с подходящ
			параметър, например  “-o result.txt”. Ако този параметър е изпуснат, се избира име по подразбиране;
			(o) Да се осигури възможност за „quiet“ режим на работа на програмата, при който се извежда
			само времето отделено за изчисление на e , отново чрез подходящо избран друг команден параметър –
			например “-q”;
		
		*/
		int precision = 1500;
		int tasks = 1;
		boolean quiet = false;
		String file_name = "result.txt";
		
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
		
		long startTime = System.currentTimeMillis();
		
		int p_digits = precision;
		int thread_count = tasks;
		int chunk_size = p_digits / thread_count;
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
		
		for(int i = 0; i < thread_count; i++) {
			try {
				tr[i].join();
				sum = sum.add(res[i]);
			} catch (InterruptedException e) {
				
			}
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("result: " + sum);
		System.out.println(elapsedTime + "ms");
	}
}
