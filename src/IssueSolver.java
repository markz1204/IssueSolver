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
public class IssueSolver {

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

        for (int i = 1; i <= N; i++) {
            if (i == 1) continue;
            int margin = i - C;
            if (margin > 0) {
                sum = sum.add(populateList(margin, i).parallel().reduce(BigInteger.ONE, (x, y) -> x.multiply(y)));
            } else {
                sum = sum.add(populateList(1, i).parallel().reduce(BigInteger.ONE, (x, y) -> x.multiply(y)));
            }
        }

        System.out.println("Result: " + sum);

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
}