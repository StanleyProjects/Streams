package stan.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class SortOutAndGrouping
{
    static void sample()
    {
        System.out.println("Sort out and grouping:");
        cutSample(Arrays.asList(12, 567, 23, 54, 68, -2, 34, 0, -4));
        tailHeadSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
    }

    static private void cutSample(List<Integer> source)
    {
        System.out.println("\t- java 8 cut for: " + source);
        long time = System.nanoTime();
        cutJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams cut for: " + source);
        time = System.nanoTime();
        cutStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void cutJava8Sample(List<Integer> source)
    {
        int start = 3;
        int end = 7;
        System.out.println("Result from " +start+ " to " + end + ": "
            + source.stream()
                    .sorted(Integer::compareTo)
                    .collect(Collectors.toList())
                    .subList(start, end));
    }
    static private void cutStreamsSample(List<Integer> source)
    {
        int start = 3;
        int end = 7;
        System.out.println("Result from " +start+ " to " + end + ": "
            + Streams.from(source)
                     .cut(Integer::compareTo, start, end)
                     .turn(To.list()));
    }

    static private void tailHeadSample(List<String> source)
    {
        System.out.println("\t- java 8 tail and head for: " + source);
        long time = System.nanoTime();
        tailHeadJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams tail and head for: " + source);
        time = System.nanoTime();
        tailHeadStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void tailHeadJava8Sample(List<String> source)
    {
        int count = 4;
        System.out.println("Tail with " + count + " count: "
            + source.stream()
                    .sorted(String::compareTo)
                    .limit(count)
                    .collect(Collectors.toList()));
        System.out.println("Head with " + count + " count: "
            + source.stream()
                    .sorted(String::compareTo)
                    .collect(Collectors.toList())
                    .subList(source.size()-count, source.size()));
    }
    private static void tailHeadStreamsSample(List<String> source)
    {
        int count = 4;
        System.out.println("Tail with " + count + " count: "
            + Streams.from(source)
                     .tail(String::compareTo, count)
                     .turn(To.list()));
        System.out.println("Head with " + count + " count: "
            + Streams.from(source)
                     .head(String::compareTo, count)
                     .turn(To.list()));
    }
}