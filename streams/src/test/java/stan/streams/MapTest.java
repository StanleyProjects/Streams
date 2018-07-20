package stan.streams;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import stan.streams.functions.Function;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class MapTest
    extends MainTest
{
    @Test
    public void mapStringToIntegerRandomNotEmptyListTest()
    {
        mapStringListToIntegerListTest(nextStringList());
    }
    @Test
    public void mapStringToIntegerEmptyListTest()
    {
        mapStringListToIntegerListTest(Collections.<String>emptyList());
    }
    @Test
    public void mapIntegerToStringRandomNotEmptyListTest()
    {
        mapIntegerListToStringListTest(nextIntegerList());
    }
    @Test
    public void mapIntegerToStringEmptyListTest()
    {
        mapIntegerListToStringListTest(Collections.<Integer>emptyList());
    }

    private void mapStringListToIntegerListTest(List<String> data)
    {
        int sum1 = 0;
        for(String it: data)
        {
            sum1 += it.length();
        }
        List<Integer> result = Streams.from(data).map(new Function<String, Integer>()
        {
            public Integer apply(String it)
            {
                return it.length();
            }
        }).list();
        int sum2 = 0;
        for(int it: result)
        {
            sum2 += it;
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void mapIntegerListToStringListTest(List<Integer> data)
    {
        int sum1 = 0;
        for(int it: data)
        {
            sum1 += (it + "_" + it*2 + "_" + it/2 + "_" + it*it).hashCode();
        }
        List<String> result = Streams.from(data).map(new Function<Integer, String>()
        {
            public String apply(Integer it)
            {
                return it + "_" + it*2 + "_" + it/2 + "_" + it*it;
            }
        }).list();
        int sum2 = 0;
        for(String it: result)
        {
            sum2 += it.hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
}