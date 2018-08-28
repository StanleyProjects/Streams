package stan.streams.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.functions.Function;

public final class NewWaysToCreate
{
    static public void sample()
    {
        System.out.println("New ways to create:");
        rangeSample();
        stringSample("My super string!");
    }

    static private void rangeSample()
    {
        int from = 2;
        int count = 11;
        System.out.println("\t- old range from " + from + " count " + count);
        long time = System.nanoTime();
        rangeOldSample(from, count);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams range from " + from + " count " + count);
        time = System.nanoTime();
        rangeStreamsSample(from, count);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    static private void rangeOldSample(int from, int count)
    {
        int[] result = new int[count];
        for(int i=0; i<result.length; i++) result[i] = from + i;
        System.out.println("Range from " +from+ " count " +count+ ": " + Arrays.toString(result));
    }
    static private void rangeStreamsSample(int from, int count)
    {
        System.out.println("Range from " +from+ " count " +count+ ": " + Streams.range(from, count).turn(To.<Integer>list()));
    }
    static private void stringSample(String string)
    {
        System.out.println("\t- old string manipulation for: " + string);
        long time = System.nanoTime();
        stringOldSample(string);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
        System.out.println("\t- streams string manipulation for: " + string);
        time = System.nanoTime();
        stringStreamsSample(string);
        System.out.println("\ttime: " + (System.nanoTime() - time)/1000);
    }
    private static void stringOldSample(String string)
    {
        List<Character> characters = new ArrayList<Character>();
        for(char it: string.toCharArray()) if(it != 'p' && it != 'r' && it != 's') characters.add(it);
        char[] result = new char[characters.size()];
        for(int i=0; i< characters.size(); i++) result[i] = characters.get(i);
        System.out.println("Without 'p', 'r', 's' characters: " + String.valueOf(result));
    }
    private static void stringStreamsSample(String string)
    {
        System.out.println("Without 'p', 'r', 's' characters: " + Streams.from(string).filter(new Function<Character, Boolean>()
        {
            public Boolean apply(Character it)
            {
                return it != 'p' && it != 'r' && it != 's';
            }
        }).turn(To.string).toString());
    }
}
