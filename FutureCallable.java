package main;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

public class FutureCallable implements Callable<Integer>{

	@Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        
        Random r= new Random();
		int rand= r.nextInt(6000);
		int result = calculatePrimCount(rand);
		
        return result;
    }
	
	private int calculatePrimCount(long load) {
		int count = 0;
		for(long i = 0; i <= load; i++){
			if(isPrim(i))
				count++;
		}
		return count;
	}

	private boolean isPrim(final long value) { 
		if (value <= 2) { 
			return (value == 2); 
		} 
		for (long i = 2; i*i <= value; i++) { 
			if (value % i == 0) { 
				return false; 
			} 
		} 
		return true;
	}
	
	public static void main(String[] args) {
		
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ArrayList<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        ArrayList<String> resultList = new ArrayList<String>();

        Callable<Integer> callable = new FutureCallable();
        
        for(int i = 0; i < 20; i++) {
            Future<Integer> future = executor.submit(callable);
            futureList.add(future);
        }
        
        for(Future<Integer> future : futureList){
            try {
            	resultList.add(new Date() + " :: Anzahl von Primzahlen der analysierten Zahlen :: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        for(String string : resultList){
        	System.out.println(string);
        }
        executor.shutdown();
	}
}
