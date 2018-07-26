package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import stan.streams.functions.BiConsumer;

class ImplementCollectFeature
{
    static void sample()
    {
        System.out.println("Collect feature:");
        turnCollectionToIntegerSample(Arrays.asList(23, 76, -12, 345, 10));
        turnStreamToCollectionSample(Arrays.asList("aa", -1, false, 0.004d, -34.1245f, '+'));
    }

    static private void turnCollectionToIntegerSample(Collection<Integer> source)
    {
        System.out.println("\t- old turn list to integer for: " + source);
        long time = System.nanoTime();
        turnCollectionToIntegerOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams turn list to integer for: " + source);
        time = System.nanoTime();
        turnCollectionToIntegerStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void turnCollectionToIntegerOldSample(Collection<Integer> source)
    {
        int sum = 0;
        for(int it: source)
        {
            sum += it;
        }
        System.out.println("Sum: " + sum);
    }
    private static void turnCollectionToIntegerStreamsSample(Collection<Integer> source)
    {
        System.out.println("Sum: " + Streams.from(source).turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Integer>()
        {
            public void accept(AtomicInteger r, Integer it)
            {
                r.getAndAdd(it);
            }
        }).intValue());
    }

    static private <T> void turnStreamToCollectionSample(Collection<T> source)
    {
        System.out.println("\t- turn stream to collection for: " + source);
        long time = System.nanoTime();
        turnStreamToCollectionStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private <T> void turnStreamToCollectionStreamsSample(Collection<T> source)
    {
        System.out.println(Streams.from(source).turn(To.<T>list()));
    }
}