package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\tJava 8 Streams demonstration");
        System.out.println("Basic functions:");
        foreachSample(Arrays.asList(1, 2, 3, 4, 5));
        mapSample(Arrays.asList("aa", "bbb", "cccc", "dd", "e"));
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
}