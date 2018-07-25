package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

class ImplementBasicFunctions
{
    static void sample()
    {
        System.out.println("Basic functions:");
        foreachSample(Arrays.asList(1, 2, 3, 4, 5));
        mapSample(Arrays.asList("aa", "bbb", "cccc", "dd", "e"));
        filterSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
    }

    static private void foreachSample(Collection<?> source)
    {
        System.out.println("\t- java 8 foreach for: " + source);
        long time = System.nanoTime();
        foreachJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams foreach for: " + source);
        time = System.nanoTime();
        foreachStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void foreachJava8Sample(Collection<?> collection)
    {
        collection.forEach(it -> System.out.println("n: " + it));
    }
    static private void foreachStreamsSample(Collection<?> collection)
    {
        Streams.from(collection)
               .foreach(it -> System.out.println("n: " + it));
    }

    static private void mapSample(Collection<String> source)
    {
        System.out.println("\t- java 8 map for: " + source);
        long time = System.nanoTime();
        mapJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams map for: " + source);
        time = System.nanoTime();
        mapStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void mapJava8Sample(Collection<String> source)
    {
        System.out.println(source.stream()
                                 .map(String::length)
                                 .collect(Collectors.toList()));
    }
    static private void mapStreamsSample(Collection<String> source)
    {
        System.out.println(Streams.from(source)
                                  .map(String::length)
                                  .list());
    }

    static private void filterSample(Collection<String> source)
    {
        System.out.println("\t- java 8 filter for: " + source);
        long time = System.nanoTime();
        filterJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams filter for: " + source);
        time = System.nanoTime();
        filterStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void filterJava8Sample(Collection<String> source)
    {
        System.out.println(source.stream()
                                 .filter(it -> it.toLowerCase().startsWith("c")
                                     || it.toLowerCase().startsWith("p"))
                                 .collect(Collectors.toList()));
    }
    private static void filterStreamsSample(Collection<String> source)
    {
        System.out.println(Streams.from(source)
                                  .filter(it -> it.toLowerCase().startsWith("c")
                                      || it.toLowerCase().startsWith("p"))
                                  .list());
    }

    private ImplementBasicFunctions()
    {}
}