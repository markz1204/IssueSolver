import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by markz on 28/2/17.
 */
public class IssueSolver {
    static int TOTAL = 100;
    static int C = 10;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BigInteger sum = BigInteger.ZERO;

        int processPerThread = 1000000;
        int threadCount = TOTAL%processPerThread == 0 ? TOTAL/processPerThread : TOTAL/processPerThread+1;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        Set<Callable<BigInteger>> tasks = new HashSet<>();

        for(int i=1; i<=threadCount; i++){
            if(i==threadCount){
                System.out.println("In " + i + ": " + ((i - 1) * processPerThread + 1) + " to " + TOTAL);
                tasks.add(new Task( (i-1) * processPerThread + 1, TOTAL));
            }else {
                System.out.println("In " + i + ": " + ((i - 1) * processPerThread + 1) + " to " + (i * processPerThread));
                tasks.add(new Task( (i-1) * processPerThread + 1, i * processPerThread));
            }
        }

        long startTime = System.currentTimeMillis();

        List<Future<BigInteger>> futures = executorService.invokeAll(tasks);

        for(Future<BigInteger> future : futures){
            sum = sum.add(future.get());
        }

        executorService.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("Result: " + sum);

        long totalTime = endTime - startTime;
        System.out.println("Takes: " + totalTime/1000 + "s");

        /*
        BigInteger sum = BigInteger.ZERO;

        long startTime = System.currentTimeMillis();

        for(int i=1; i<=TOTAL; i++){

            if (i==1) continue;

            int margin = i -C;

            if(margin > 0){
                sum = sum.add(populateList(margin, i).parallel().reduce(BigInteger.ONE, (x, y)-> x.multiply(y)));
            }else{
                sum = sum.add(populateList(1, i).parallel().reduce(BigInteger.ONE, (x, y)-> x.multiply(y)));
            }

        }

        System.out.println("Result: " + sum);
        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;
        System.out.println("Takes: " + totalTime/1000 + "s");*/
    }

    static Stream<BigInteger> populateList(int start, int end){

        return IntStream.range(start, end).mapToObj((num)->BigInteger.valueOf(num));

    }

    private static class Task implements Callable<BigInteger>{

        int start, end;

        Task(int start, int end){
            this.start = start;
            this.end = end;
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
