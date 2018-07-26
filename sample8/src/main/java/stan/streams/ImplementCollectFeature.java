package stan.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        System.out.println("\t- java 8 turn list to integer for: " + source);
        long time = System.nanoTime();
        turnCollectionToIntegerJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams turn list to integer for: " + source);
        time = System.nanoTime();
        turnCollectionToIntegerStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void turnCollectionToIntegerJava8Sample(Collection<Integer> source)
    {
        System.out.println("Sum: " + source.stream()
                                           .collect(AtomicInteger::new,
                                               AtomicInteger::getAndAdd,
                                               (left, right) -> left.getAndAdd(right.intValue()))
                                           .intValue());
    }
    private static void turnCollectionToIntegerStreamsSample(Collection<Integer> source)
    {
        System.out.println("Sum: " + Streams.from(source)
                                            .turn(new AtomicInteger(), AtomicInteger::getAndAdd)
                                            .intValue());
    }

    static private void turnStreamToCollectionSample(Collection<?> source)
    {
        System.out.println("\t- java 8 turn stream to collection for: " + source);
        long time = System.nanoTime();
        turnStreamToCollectionJava8Sample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams turn stream to collection for: " + source);
        time = System.nanoTime();
        turnStreamToCollectionStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void turnStreamToCollectionJava8Sample(Collection<?> source)
    {
        System.out.println(source.stream()
                                 .collect(Collectors.toList()));
    }
    private static void turnStreamToCollectionStreamsSample(Collection<?> source)
    {
        System.out.println(Streams.from(source)
                                  .turn(To.list()));
    }
}