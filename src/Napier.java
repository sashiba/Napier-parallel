import java.math.*;

public class Napier implements Runnable {
	
	BigDecimal r[];
	int chunk_size;
	int num;
	int thread_count;
	boolean isQuiet;
	
	public static BigDecimal factorial(BigDecimal n) {
	    BigDecimal result = BigDecimal.ONE;

	    while (!n.equals(BigDecimal.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigDecimal.ONE);
	    }

	    return result;
	}
	public Napier(BigDecimal r[], int chunk_size, int num, int thread_count, boolean isQuiet) {
		this.r = r;
		this.chunk_size = chunk_size;
		this.num = num;
		this.thread_count = thread_count;
		this.isQuiet = isQuiet;
	}
	
	public void run() {
	
		long startTime = System.currentTimeMillis();
		BigDecimal napier = BigDecimal.valueOf(0);
		int p_digits = chunk_size * thread_count;
		
		int start = num * chunk_size;
		int end = (num + 1) * chunk_size - 1;
		
		if(!isQuiet) System.out.println(Thread.currentThread().getName() + " started.");
		
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
		// ot for <, nqma da wzeme poslednata stoinost na interwala
		napier = napier.add(chislitel.divide(znamenatel, p_digits, RoundingMode.CEILING));
	
		r[num] = napier;
		if(!isQuiet){
			System.out.println(Thread.currentThread().getName() + " stopped.");
			System.out.println(Thread.currentThread().getName() + " execution time was "
									+ (System.currentTimeMillis() - startTime) + " ms");
		}
	}
}