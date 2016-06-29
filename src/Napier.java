import java.math.*;

public class Napier implements Runnable {
	
	//double a[];
	BigDecimal a[];
	BigDecimal r[];
	int chunk_size;
	int num;
	int thread_count;
	
	public static BigDecimal factorial(BigDecimal n) {
	    BigDecimal result = BigDecimal.ONE;

	    while (!n.equals(BigDecimal.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigDecimal.ONE);
	    }

	    return result;
	}
	public Napier(BigDecimal a[], BigDecimal r[], int chunk_size, int num, int thread_count) {
		this.a = a;
		this.r = r;
		this.chunk_size = chunk_size;
		this.num = num;
		this.thread_count = thread_count;
	}
	
	public void run() {
	/*	int start = num * chunk_size;
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
		}*/
		long startTime = System.currentTimeMillis();
		BigDecimal napier = BigDecimal.valueOf(0);
		int p_digits = chunk_size * thread_count;
		// purwiq term
		
		int start = num * chunk_size;
		int end = (num + 1) * chunk_size - 1;
		System.out.println("start=" + start + " stop=" + end);
		System.out.println(Thread.currentThread().getName() + " started");
		
		
		BigDecimal chislitel, znamenatel;
		
		chislitel = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(start)).add(BigDecimal.ONE);
		znamenatel = factorial(BigDecimal.valueOf(start*2));
		napier = napier.add(chislitel.divide(znamenatel, p_digits, RoundingMode.CEILING));
		
		for(BigDecimal term = BigDecimal.valueOf(start+1); term.compareTo(BigDecimal.valueOf(end)) < 0; 
				term = term.add(BigDecimal.ONE)){
			chislitel = chislitel.add(BigDecimal.valueOf(2));
			znamenatel = znamenatel.multiply((term.multiply(BigDecimal.valueOf(2))
					.subtract(BigDecimal.ONE))
					.multiply(term.multiply(BigDecimal.valueOf(2))));
			napier = napier.add(chislitel.divide(znamenatel, p_digits, RoundingMode.CEILING));
		}
		
		chislitel = chislitel.add(BigDecimal.valueOf(2));
		znamenatel = znamenatel.multiply(BigDecimal.valueOf(2*end - 1)).multiply(BigDecimal.valueOf(2*end));
		
		napier = napier.add(chislitel.divide(znamenatel, p_digits, RoundingMode.CEILING));
	
		r[num] = napier;
		System.out.println(Thread.currentThread().getName() + " finished");
		System.out.println(Thread.currentThread().getName() + " execution time was " + (System.currentTimeMillis() - startTime) + " ms");
	}
	
}