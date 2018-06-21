package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\tJava 8 Streams demonstration");
        System.out.println("Basic functions:");
        System.out.println("\t- java 8 foreach");
        List<?> source = Arrays.asList(1, 2, 3, 4, 5);
        foreachJava8Sample(source);
        System.out.println("\t- streams foreach");
        foreachStreamsSample(source);
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
}