package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import stan.streams.functions.Consumer;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\tStreams demonstration");
        System.out.println("Basic functions:");
        System.out.println("\t- old foreach");
        List<?> source = Arrays.asList(1, 2, 3, 4, 5);
        foreachOldSample(source);
        System.out.println("\t- streams foreach");
        foreachStreamsSample(source);
    }

    static private <T> void foreachOldSample(Collection<T> collection)
    {
        for(T it: collection)
        {
            System.out.println("n: " + it);
        }
    }
    static private <T> void foreachStreamsSample(Collection<T> collection)
    {
        Streams.from(collection)
               .foreach(new Consumer<T>()
               {
                   public void accept(T it)
                   {
                       System.out.println("n: " + it);
                   }
               });
    }
}