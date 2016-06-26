import java.math.*;

public class Napier implements Runnable {
	
	//double a[];
	BigDecimal a[];
	BigDecimal r[];
	int chunk_size;
	int num;
	int thread_count;
	
	/*public static long factorial(long n) {
        long fact = 1; 
        for (long i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	
	public static BigInteger factorial(BigInteger n) {
	    BigInteger result = BigInteger.ONE;

	    while (!n.equals(BigInteger.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigInteger.ONE);
	    }

	    return result;
	}*/
	
	public static BigDecimal factorial(BigDecimal n) {
	    BigDecimal result = BigDecimal.ONE;

	    while (!n.equals(BigDecimal.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigDecimal.ONE);
	    }

	    return result;
	}
	//public Napier(double a[], BigDecimal r[], int chunk_size, int num, int thread_count) {
	public Napier(BigDecimal a[], BigDecimal r[], int chunk_size, int num, int thread_count) {
		this.a = a;
		this.r = r;
		this.chunk_size = chunk_size;
		this.num = num;
		this.thread_count = thread_count;
	}
	
	public void run() {
		int start = num * chunk_size;
		int end = (num+1) * chunk_size - 1;
		long startTime = System.currentTimeMillis();
		int p_digits = chunk_size * thread_count;

		System.out.println("start=" + start + " stop=" + end);
		System.out.println(Thread.currentThread().getName() + " started");
		if (num == (thread_count - 1)) end = a.length - 1;
		
		r[num] = BigDecimal.valueOf(0);
		for (int i = start; i <= end; i++){
			//a[i] = BigDecimal.valueOf((Math.pow(3*i, 2) + 1)/(factorial(3*i)));
			a[i] = BigDecimal.valueOf((Math.pow(3*i, 2) + 1)).divide((factorial(BigDecimal.valueOf(3*i))),p_digits, RoundingMode.CEILING );
			r[num] = r[num].add(a[i]);
			//r[num] = r[num].add(BigDecimal.valueOf(a[i]));
		}

		System.out.println(Thread.currentThread().getName() + " finished");
		System.out.println(Thread.currentThread().getName() + " execution time was " + (System.currentTimeMillis() - startTime) + " ms");
	}
	
	/*public static void main(String[] args){
		double sum = 0;
		//int limit = Integer.parseInt(args[0]);
		int limit = 20;
		for (int i = 0; i <= limit; i++){
			sum += ((3*i)^2+1)/factorial(3*i);
		}	
		
		System.out.println(sum);
	}*/
	
}