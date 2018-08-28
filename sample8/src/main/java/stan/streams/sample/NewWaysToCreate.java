package stan.streams.sample;

import java.util.Arrays;
import java.util.stream.IntStream;

import stan.streams.Streams;
import stan.streams.To;

public final class NewWaysToCreate
{
    static public void sample()
    {
        System.out.println("New ways to create:");
        rangeSample();
        stringSample("My super string!");
    }

    static private void rangeSample()
    {
        int from = 2;
        int count = 11;
        System.out.println("\t- old range from " + from + " count " + count);
        long time = System.nanoTime();
        rangeJava8Sample(from, count);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams range from " + from + " count " + count);
        time = System.nanoTime();
        rangeStreamsSample(from, count);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void rangeJava8Sample(int from, int count)
    {
        System.out.println("Range from " +from+ " count " +count+ ": " + Arrays.toString(IntStream.range(from, from+count).toArray()));
    }
    static private void rangeStreamsSample(int from, int count)
    {
        System.out.println("Range from " +from+ " count " +count+ ": " + Streams.range(from, count).turn(To.list()));
    }
    static private void stringSample(String string)
    {
        System.out.println("\t- old string manipulation for: " + string);
        long time = System.nanoTime();
        stringJava8Sample(string);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams string manipulation for: " + string);
        time = System.nanoTime();
        stringStreamsSample(string);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void stringJava8Sample(String string)
    {
        System.out.println("Without 'p', 'r', 's' characters: " + string.chars()
                                                                        .mapToObj(i -> (char)i)
                                                                        .filter(it -> it != 'p' && it != 'r' && it != 's')
                                                                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                                                        .toString());
    }
    private static void stringStreamsSample(String string)
    {
        System.out.println("Without 'p', 'r', 's' characters: " + Streams.from(string)
                                                                         .filter(it -> it != 'p' && it != 'r' && it != 's')
                                                                         .turn(To.string)
                                                                         .toString());
    }
}