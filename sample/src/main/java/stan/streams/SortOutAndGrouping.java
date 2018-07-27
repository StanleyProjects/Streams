package stan.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        System.out.println("\t- old cut for: " + source);
        long time = System.nanoTime();
        tailHeadOldSample(source);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams cut for: " + source);
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
}