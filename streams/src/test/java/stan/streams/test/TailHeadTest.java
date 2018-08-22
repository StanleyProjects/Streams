package stan.streams.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TailHeadTest
    extends MainTest
{
    @Test
    public void tailObjectsListInTheMiddle()
    {
        int count = 1000 + nextInt(1000);
        tailObjectsList(count,
            count - count/6 - nextInt(count/4));
    }
    @Test
    public void tailObjectsListMoreThenHead()
    {
        int count = 1000 + nextInt(1000);
        tailObjectsList(count,
            count + count/6 + nextInt(count/4));
    }
    @Test
    public void tailObjectsListEmpty()
    {
        int count = 1000 + nextInt(1000);
        tailObjectsList(count, 0);
    }
    @Test
    public void tailObjectsListOne()
    {
        int count = 1000 + nextInt(1000);
        tailObjectsList(count, 1);
    }
    @Test
    public void headObjectsListInTheMiddle()
    {
        int count = 1000 + nextInt(1000);
        headObjectsList(count,
            count/6 + nextInt(count/4));
    }
    @Test
    public void headObjectsListLessThenTail()
    {
        int count = 1000 + nextInt(1000);
        headObjectsList(count,
            count + count/6 + nextInt(count/4));
    }
    @Test
    public void headObjectsListEmpty()
    {
        int count = 1000 + nextInt(1000);
        headObjectsList(count, 0);
    }
    @Test
    public void headObjectsListOne()
    {
        int count = 1000 + nextInt(1000);
        headObjectsList(count, 1);
    }
    @Test
    public void headObjectsListWrongValue()
    {
        int size = 1000 + nextInt(1000);
        int count = -nextInt(size/4);
        try
        {
            Streams.from(nextList(count))
                   .head(hascodeObjectComparator, count)
                   .turn(To.list());
        }
        catch(IndexOutOfBoundsException e)
        {
            return;
        }
        catch(Exception e)
        {
            fail("Should work but: " + e.getMessage());
        }
        fail("IndexOutOfBoundsException must be thrown with count: " + count + "!");
    }
    @Test
    public void tailObjectsListWrongValue()
    {
        int size = 1000 + nextInt(1000);
        int count = -nextInt(size/4);
        try
        {
            Streams.from(nextList(size))
                   .tail(hascodeObjectComparator, count)
                   .turn(To.list());
        }
        catch(IndexOutOfBoundsException e)
        {
            return;
        }
        catch(Exception e)
        {
            fail("Should work but: " + e.getMessage());
        }
        fail("IndexOutOfBoundsException must be thrown with count: " + count + "!");
    }

    private void tailObjectsList(int size, int count)
    {
        List<Object> source = nextList(size);
        int sum1 = 0;
        for(Object it: cutObjectsList(new ArrayList<Object>(source), 0, count))
        {
            sum1 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        int sum2 = 0;
        for(Object it: Streams.from(new ArrayList<Object>(source))
                              .tail(hascodeObjectComparator, count)
                              .turn(To.list()))
        {
            sum2 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private void headObjectsList(int size, int count)
    {
        List<Object> source = nextList(size);
        int sum1 = 0;
        for(Object it: cutObjectsList(new ArrayList<Object>(source), size - count, size))
        {
            sum1 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        int sum2 = 0;
        for(Object it: Streams.from(new ArrayList<Object>(source))
                              .head(hascodeObjectComparator, count)
                              .turn(To.list()))
        {
            sum2 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
    private Object[] cutObjectsList(List<Object> source, int start, int end)
    {
        Collections.sort(source, hascodeObjectComparator);
        int s = start < 0 ? 0 : start;
        int e = end > source.size() ? source.size() : end;
        Object[] result = new Object[s >= source.size() ? 0 : end < 0 ? 0 : e-s];
        for(int i=s; i<e; i++)
        {
            result[i-s] = source.get(i);
        }
        return result;
    }
}