import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by markz on 28/2/17.
 */
public class IssueSolverMultiThread {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("****Enter 'q' to exit this program if you want****");


        System.out.print("Enter a integer for N: ");

        String nInput = scanner.next();

        if("q".equals(nInput)) return;

        int N = Integer.parseInt(nInput);

        System.out.print("Enter a integer for C: ");

        String cInput = scanner.next();

        if("q".equals(cInput)) return;

        int C = Integer.parseInt(cInput);

        BigInteger sum = BigInteger.ZERO;

        System.out.println("Start working:  (N= " + N + ", C=" + C + ")");

        int processPerThread = 100000;
        int threadCount = N%processPerThread == 0 ? N/processPerThread : N/processPerThread+1;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        Set<Callable<BigInteger>> tasks = new HashSet<>();

        for(int i=1; i<=threadCount; i++){
            if(i==threadCount){
                tasks.add(new Task( (i-1) * processPerThread + 1, N, C));
            }else {
                tasks.add(new Task( (i-1) * processPerThread + 1, i * processPerThread, C));
            }
        }

        long startTime = System.currentTimeMillis();

        List<Future<BigInteger>> futures = null;
        try {
            futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Future<BigInteger> future : futures){
            try {
                sum = sum.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("Result: " + sum);

        long totalTime = endTime - startTime;
        System.out.println("Takes: " + totalTime/1000 + "s");

    }

    /**
     * Generate an int range stream for further processing.
     * @param start the initial value.
     * @param end the end bound.
     * @return
     */
    static Stream<BigInteger> populateList(int start, int end){

        return IntStream.range(start, end).mapToObj((num)->BigInteger.valueOf(num));

    }

    private static class Task implements Callable<BigInteger>{

        int start, end, C;

        Task(int start, int end, int C){
            this.start = start;
            this.end = end;
            this.C = C;
        }

        @Override
        public BigInteger call() throws Exception {
            BigInteger sum = BigInteger.ZERO;

            for(int i = start; i<=end; i++) {
                if (i == 1) continue;

                int margin = i - C;

                if (margin > 0) {
                    sum = sum.add(populateList(margin, i).parallel().reduce(BigInteger.ONE, (x, y) -> x.multiply(y)));
                } else {
                    sum = sum.add(populateList(1, i).parallel().reduce(BigInteger.ONE, (x, y) -> x.multiply(y)));
                }
            }

            return sum;
        }
    }
}