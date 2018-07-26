package stan.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import stan.streams.functions.Consumer;
import stan.streams.functions.Function;
import stan.streams.functions.Predicate;

class ImplementBasicFunctions
{
    static void sample()
    {
        System.out.println("Basic functions:");
        foreachSample(Arrays.asList(1, 2, 3, 4, 5));
        mapSample(Arrays.asList("aa", "bbb", "cccc", "dd", "e"));
        filterSample(Arrays.asList("Cat", "Dog", "Penguin", "Platypus", "Elephant", "Camel", "Goat", "Lion", "Turtle", "Crab"));
    }

    static private <T> void foreachSample(Collection<T> source)
    {
        System.out.println("\t- old foreach for: " + source);
        long time = System.nanoTime();
        foreachOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams foreach for: " + source);
        time = System.nanoTime();
        foreachStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
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

    static private void mapSample(Collection<String> source)
    {
        System.out.println("\t- old map for: " + source);
        long time = System.nanoTime();
        mapOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams map for: " + source);
        time = System.nanoTime();
        mapStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void mapOldSample(Collection<String> source)
    {
        List<Integer> dist = new ArrayList<Integer>(source.size());
        for(String it: source)
        {
            dist.add(it.length());
        }
        System.out.println(dist);
    }
    static private void mapStreamsSample(Collection<String> source)
    {
        System.out.println(Streams.from(source)
                                  .map(new Function<String, Integer>()
                                  {
                                      public Integer apply(String it)
                                      {
                                          return it.length();
                                      }
                                  })
                                  .turn(To.<Integer>list()));
    }

    static private void filterSample(Collection<String> source)
    {
        System.out.println("\t- old filter for: " + source);
        long time = System.nanoTime();
        filterOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams filter for: " + source);
        time = System.nanoTime();
        filterStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void filterOldSample(Collection<String> source)
    {
        List<String> result = new ArrayList<String>();
        for(String it: source)
        {
            if(it.toLowerCase().startsWith("c")
                || it.toLowerCase().startsWith("p"))
            {
                result.add(it);
            }
        }
        System.out.println(result);
    }
    private static void filterStreamsSample(Collection<String> source)
    {
        System.out.println(Streams.from(source)
                                  .filter(new Predicate<String>()
                                  {
                                      public boolean test(String it)
                                      {
                                          return it.toLowerCase().startsWith("c")
                                              || it.toLowerCase().startsWith("p");
                                      }
                                  })
                                  .turn(To.<String>list()));
    }

    private ImplementBasicFunctions()
    {}
}
