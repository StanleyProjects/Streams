package stan.streams;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

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