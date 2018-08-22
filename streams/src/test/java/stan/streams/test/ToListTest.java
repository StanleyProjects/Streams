package stan.streams.test;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class ToListTest
    extends MainTest
{
    @Test
    public void turnStringStreamToStringListRandomNotEmptyListTest()
    {
        turnStringStreamToStringList(nextStringList());
    }
    @Test
    public void turnStringStreamToStringListEmptyListTest()
    {
        turnStringStreamToStringList(Collections.<String>emptyList());
    }
    @Test
    public void turnIntegerStreamToIntegerListRandomNotEmptyListTest()
    {
        turnIntegerStreamToIntegerList(nextIntegerList());
    }
    @Test
    public void turnIntegerStreamToIntegerListEmptyListTest()
    {
        turnIntegerStreamToIntegerList(Collections.<Integer>emptyList());
    }
    @Test
    public void turnObjectStreamToObjectListRandomNotEmptyListTest()
    {
        turnObjectStreamToObjectList(nextList());
    }
    @Test
    public void turnObjectStreamToObjectListEmptyListTest()
    {
        turnObjectStreamToObjectList(Collections.emptyList());
    }

    private void turnStringStreamToStringList(List<String> data)
    {
        int res1 = 0;
        for(String it: data)
        {
            res1 += it.hashCode();
        }
        int res2 = 0;
        for(String it: Streams.from(data).turn(To.<String>list()))
        {
            res2 += it.hashCode();
        }
        assertEquals("Results must be equals!", res1, res2);
    }
    private void turnIntegerStreamToIntegerList(List<Integer> data)
    {
        int sum1 = 0;
        for(int it: data)
        {
            sum1 += it*2 + it/2 + it*it;
        }
        int sum2 = 0;
        for(int it: Streams.from(data).turn(To.<Integer>list()))
        {
            sum2 += it*2 + it/2 + it*it;
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void turnObjectStreamToObjectList(List<Object> data)
    {
        int res1 = 0;
        for(Object it: data)
        {
            res1 += it.hashCode();
        }
        int res2 = 0;
        for(Object it: Streams.from(data).turn(To.list()))
        {
            res2 += it.hashCode();
        }
        assertEquals("Results must be equals!", res1, res2);
    }
}