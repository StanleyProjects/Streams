package stan.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Consumer;
import stan.streams.functions.Function;

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
        System.out.println("\t- old cut for: " + source);
        long time = System.nanoTime();
        cutOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams cut for: " + source);
        time = System.nanoTime();
        cutStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void cutOldSample(List<Integer> source)
    {
        List<Integer> copy = new ArrayList<Integer>(source);
        int start = 3;
        int end = 7;
        int s = start < 0 ? 0 : start;
        int e = end > copy.size() ? copy.size() : end;
        int[] result = new int[s >= copy.size() ? 0 : end < 0 ? 0 : e-s];
        Collections.sort(copy);
        for(int i=s; i<e; i++)
        {
            result[i-s] = copy.get(i);
        }
        System.out.println("Result from " +start+ " to " + end + ": " + Arrays.toString(result));
    }
    static private void cutStreamsSample(List<Integer> source)
    {
        int start = 3;
        int end = 7;
        System.out.println("Result from " +start+ " to " + end + ": " + Streams.from(source).cut(new Comparator<Integer>()
        {
            public int compare(Integer o1, Integer o2)
            {
                return o1 < o2 ? -1 : o1 > o2 ? 1 : 0;
            }
        }, start, end).turn(To.<Integer>list()));
    }

    static private void tailHeadSample(List<String> source)
    {
        System.out.println("\t- old tail/head for: " + source);
        long time = System.nanoTime();
        tailHeadOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams tail/head for: " + source);
        time = System.nanoTime();
        tailHeadStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void tailHeadOldSample(List<String> source)
    {
        List<String> copy = new ArrayList<String>(source);
        int count = 4;
        int e = count > copy.size() ? copy.size() : count;
        String[] tail = new String[count < 0 ? 0 : e];
        Collections.sort(copy);
        for(int i=0; i<e; i++)
        {
            tail[i] = copy.get(i);
        }
        System.out.println("Tail with " + count + " count: " + Arrays.toString(tail));
        int s = count > copy.size() ? copy.size() : count;
        String[] head = new String[count < 0 ? 0 : s];
        for(int i=copy.size()-s; i<copy.size(); i++)
        {
            head[i-(copy.size()-s)] = copy.get(i);
        }
        System.out.println("Head with " + count + " count: " + Arrays.toString(head));
    }
    private static void tailHeadStreamsSample(List<String> source)
    {
        int count = 4;
        System.out.println("Tail with " + count + " count: " + Streams.from(source).tail(new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                return o1.compareTo(o2);
            }
        }, count).turn(To.<String>list()));
        System.out.println("Head with " + count + " count: " + Streams.from(source).head(new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                return o1.compareTo(o2);
            }
        }, count).turn(To.<String>list()));
    }

    static private void firstLastSample(List<String> source)
    {
        System.out.println("\t- old first/last for: " + source);
        long time = System.nanoTime();
        firstLastOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams first/last for: " + source);
        time = System.nanoTime();
        firstLastStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void firstLastOldSample(List<String> source)
    {
        String first;
        String last;
        if(source.isEmpty())
        {
            first = null;
            last = null;
        }
        else if(source.size() == 1)
        {
            first = source.get(0);
            last = source.get(0);
        }
        else
        {
            Collections.sort(source);
            first = source.get(0);
            last = source.get(source.size() - 1);
        }
        System.out.println("First object is \"" + first + "\"" +
            " and last object is \"" + last + "\"" +
            " in alphabetic order");
    }
    static private void firstLastStreamsSample(List<String> source)
    {
        Stream<String> stream = Streams.from(source);
        Comparator<String> comparator = new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                return o1.compareTo(o2);
            }
        };
        System.out.println("First object is \"" + stream.first(comparator) + "\"" +
            " and last object is \"" + stream.last(comparator) + "\"" +
            " in alphabetic order");
    }

    static private void groupingSample(List<String> source)
    {
        System.out.println("\t- old grouping for: " + source);
        long time = System.nanoTime();
        groupingOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams grouping for: " + source);
        time = System.nanoTime();
        groupingStreamsSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void groupingOldSample(List<String> source)
    {
        Map<String, List<String>> groups = new HashMap<String, List<String>>();
        for(String it: source)
        {
            String key = it.substring(0, 1);
            List<String> list = groups.get(key);
            if(list == null) list = new ArrayList<String>();
            list.add(it);
            groups.put(key, list);
        }
        System.out.println("Groups:");
        List<String> best = new ArrayList<String>();
        for(String key: groups.keySet())
        {
            System.out.println(key + " -> " + groups.get(key));
            List<String> group = groups.get(key);
            Collections.sort(group);
            best.add(group.get(0));
        }
        Collections.sort(best);
        System.out.println("List of the best: " + best);
    }
    private static void groupingStreamsSample(List<String> source)
    {
        System.out.println("Groups:");
        List<String> best = Streams.from(source)
                                   .group(new Function<String, String>()
                                   {
                                       public String apply(String it)
                                       {
                                           return it.substring(0, 1);
                                       }
                                   })
                                   .foreach(new Consumer<Pair<String, Stream<String>>>()
                                   {
                                       public void accept(Pair<String, Stream<String>> it)
                                       {
                                           System.out.println(it.first + " -> " + it.second.turn(To.<String>list()));
                                       }
                                   })
                                   .map(new Function<Pair<String,Stream<String>>, String>()
                                   {
                                       public String apply(Pair<String, Stream<String>> it)
                                       {
                                           return it.second.first(new Comparator<String>()
                                           {
                                               public int compare(String o1, String o2)
                                               {
                                                   return o1.compareTo(o2);
                                               }
                                           });
                                       }
                                   }).turn(To.<String>list());
        Collections.sort(best);
        System.out.println("List of the best: " + best);
    }
}