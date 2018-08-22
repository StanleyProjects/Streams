package stan.streams.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stan.streams.Pair;
import stan.streams.Streams;
import stan.streams.functions.Consumer;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class ForeachTest
    extends MainTest
{
    @Test
    public void foreachIntegerRandomNotEmptyListTest()
    {
        foreachIntegerListTest(nextIntegerList());
    }
    @Test
    public void foreachIntegerEmptyListTest()
    {
        foreachIntegerListTest(Collections.<Integer>emptyList());
    }
    @Test
    public void foreachStringRandomNotEmptyListTest()
    {
        foreachStringListTest(nextStringList());
    }
    @Test
    public void foreachStringEmptyListTest()
    {
        foreachStringListTest(Collections.<String>emptyList());
    }
    @Test
    public void foreachDifficultRandomNotEmptyListTest()
    {
        foreachDifficultListTest(Arrays.<Map<Pair<String, Double>, Integer>>asList(
            new HashMap<Pair<String, Double>, Integer>() {
                {
                    put(new Pair<String, Double>(nextString(), nextDouble()), nextInt());
                    while(nextInt(10) != 5)
                    {
                        put(new Pair<String, Double>(nextString(), nextDouble()), nextInt());
                    }
                }
            }, new HashMap<Pair<String, Double>, Integer>() {
                {
                    while(nextInt(10) != 5)
                    {
                        put(new Pair<String, Double>(nextString(), nextDouble()), nextInt());
                    }
                }
            }, new HashMap<Pair<String, Double>, Integer>() {
                {
                    while(nextInt(10) != 5)
                    {
                        put(new Pair<String, Double>(nextString(), nextDouble()), nextInt());
                    }
                }
            }, new HashMap<Pair<String, Double>, Integer>() {
                {
                    while(nextInt(10) != 5)
                    {
                        put(new Pair<String, Double>(nextString(), nextDouble()), nextInt());
                    }
                }
            }));
    }
    @Test
    public void foreachDifficultEmptyListTest()
    {
        foreachDifficultListTest(Collections.<Map<Pair<String, Double>, Integer>>emptyList());
    }
    @Test
    public void foreachObjectRandomNotEmptyListTest()
    {
        foreachObjectListTest(nextList());
    }
    @Test
    public void foreachObjectEmptyListTest()
    {
        foreachObjectListTest(Collections.emptyList());
    }

    private void foreachIntegerListTest(List<Integer> data)
    {
        Sum sum1 = new Sum();
        for(int it: data)
        {
            sum1.add(it*2);
        }
        final Sum sum2 = new Sum();
        Streams.from(data)
               .foreach(new Consumer<Integer>()
               {
                   public void accept(Integer it)
                   {
                       sum2.add(it*2);
                   }
               });
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void foreachStringListTest(List<String> data)
    {
        Sum sum1 = new Sum();
        for(String it: data)
        {
            sum1.add(it.hashCode()*2);
        }
        final Sum sum2 = new Sum();
        Streams.from(data)
               .foreach(new Consumer<String>()
               {
                   public void accept(String it)
                   {
                       sum2.add(it.hashCode()*2);
                   }
               });
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void foreachDifficultListTest(List<Map<Pair<String, Double>, Integer>> data)
    {
        Sum sum1 = new Sum();
        for(Map<Pair<String, Double>, Integer> it: data)
        {
            sum1.add(it.hashCode()*2);
            for(Pair<String, Double> key: it.keySet())
            {
                sum1.add(key.hashCode()*3);
                sum1.add(key.first.hashCode()*4);
                sum1.add((int)(key.second*5));
                sum1.add(it.get(key)*6);
            }
        }
        final Sum sum2 = new Sum();
        Streams.from(data)
               .foreach(new Consumer<Map<Pair<String, Double>, Integer>>()
               {
                   public void accept(Map<Pair<String, Double>, Integer> it)
                   {
                       sum2.add(it.hashCode()*2);
                       for(Pair<String, Double> key: it.keySet())
                       {
                           sum2.add(key.hashCode()*3);
                           sum2.add(key.first.hashCode()*4);
                           sum2.add((int)(key.second*5));
                           sum2.add(it.get(key)*6);
                       }
                   }
               });
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void foreachObjectListTest(List<Object> data)
    {
        Sum sum1 = new Sum();
        for(Object it: data)
        {
            sum1.add(it.hashCode()*2);
        }
        final Sum sum2 = new Sum();
        Streams.from(data)
               .foreach(new Consumer<Object>()
               {
                   public void accept(Object it)
                   {
                       sum2.add(it.hashCode()*2);
                   }
               });
        assertEquals("Sums must be equals!", sum1, sum2);
    }
}