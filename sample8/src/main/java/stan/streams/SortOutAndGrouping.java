package stan.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

class SortOutAndGrouping
{
    static void sample()
    {
        System.out.println("Sort out and grouping:");
        cutSample(Arrays.asList(12, 567, 23, 54, 68, -2, 34, 0, -4));
        tailHeadSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
        firstLastSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
        groupingSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
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
        System.out.println("\t- java 8 tail/head for: " + source);
        long time = System.nanoTime();
        tailHeadJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams tail/head for: " + source);
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

    static private void firstLastSample(List<String> source)
    {
        System.out.println("\t- java 8 first/last for: " + source);
        long time = System.nanoTime();
        firstLastJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams first/last for: " + source);
        time = System.nanoTime();
        firstLastStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void firstLastJava8Sample(List<String> source)
    {
        System.out.println("First object is \"" + source.stream().min(String::compareTo).get() + "\"" +
            " and last object is \"" + source.stream().max(String::compareTo).get() + "\"" +
            " in alphabetic order");
    }
    private static void firstLastStreamsSample(List<String> source)
    {
        Stream<String> stream = Streams.from(source);
        System.out.println("First object is \"" + stream.first(String::compareTo) + "\"" +
            " and last object is \"" + stream.last(String::compareTo) + "\"" +
            " in alphabetic order");
    }

    static private void groupingSample(List<String> source)
    {
        System.out.println("\t- java 8 grouping for: " + source);
        long time = System.nanoTime();
        groupingJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams grouping for: " + source);
        time = System.nanoTime();
        groupingStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void groupingJava8Sample(List<String> source)
    {
        System.out.println("Groups:");
        Map<String, List<String>> groups = source.stream()
                                                 .collect(Collectors.groupingBy(it -> it.substring(0, 1)));
        groups.forEach((key, value) -> System.out.println(key + " -> " + value));
        System.out.println("List of the best: " + groups.keySet()
                                                        .stream()
                                                        .map(key -> groups.get(key)
                                                                          .stream()
                                                                          .min(String::compareTo)
                                                                          .get())
                                                        .sorted().collect(Collectors.toList()));
    }
    private static void groupingStreamsSample(List<String> source)
    {
        System.out.println("Groups:");
        List<String> best = Streams.from(source)
                                   .group(it -> it.substring(0, 1))
                                   .foreach(it -> System.out.println(it.first + " -> " + it.second.turn(To.list())))
                                   .map(it -> it.second.first(String::compareTo))
                                   .turn(To.list());
        Collections.sort(best);
        System.out.println("List of the best: " + best);
    }
}