package stan.streams;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import stan.streams.functions.Predicate;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class FilterTest
    extends MainTest
{
    @Test
    public void filterIntegerRandomNotEmptyListTest()
    {
        filterIntegerList(nextIntegerList());
    }
    @Test
    public void filterIntegerEmptyListTest()
    {
        filterIntegerList(Collections.<Integer>emptyList());
    }
    @Test
    public void filterStringRandomNotEmptyListTest()
    {
        filterStringList(nextStringList());
    }
    @Test
    public void filterStringEmptyListTest()
    {
        filterStringList(Collections.<String>emptyList());
    }
    @Test
    public void filterObjectRandomNotEmptyListTest()
    {
        filterObjectList(nextList());
    }
    @Test
    public void filterObjectEmptyListTest()
    {
        filterObjectList(Collections.emptyList());
    }

    private void filterIntegerList(List<Integer> data)
    {
        int sum1 = 0;
        for(int it: data)
        {
            if(it > 250
                && it < 750)
            {
                sum1 += (it + "_" + it*2 + "_" + it/2 + "_" + it*it).hashCode();
            }
        }
        int sum2 = 0;
        for(int it: Streams.from(data).filter(new Predicate<Integer>()
        {
            public boolean test(Integer it)
            {
                return it > 250
                    && it < 750;
            }
        }).turn(To.<Integer>list()))
        {
            sum2 += (it + "_" + it*2 + "_" + it/2 + "_" + it*it).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void filterStringList(List<String> data)
    {
        int sum1 = 0;
        for(String it: data)
        {
            if(it.length() > 75
                && it.length() < 125)
            {
                sum1 += (it + "_" + it.length()*2 + "_" + it.length()/2 + "_" + it.length()*it.length()).hashCode();
            }
        }
        int sum2 = 0;
        for(String it: Streams.from(data).filter(new Predicate<String>()
        {
            public boolean test(String it)
            {
                return it.length() > 75
                    && it.length() < 125;
            }
        }).turn(To.<String>list()))
        {
            sum2 += (it + "_" + it.length()*2 + "_" + it.length()/2 + "_" + it.length()*it.length()).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void filterObjectList(Collection<Object> data)
    {
        int sum1 = 0;
        for(Object it: data)
        {
            if((it.hashCode()/3-1)%2 == 0)
            {
                sum1 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
            }
        }
        int sum2 = 0;
        for(Object it: Streams.from(data).filter(new Predicate<Object>()
        {
            public boolean test(Object it)
            {
                return (it.hashCode()/3-1)%2 == 0;
            }
        }).turn(To.list()))
        {
            sum2 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
}