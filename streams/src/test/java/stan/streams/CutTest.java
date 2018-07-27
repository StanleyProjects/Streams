package stan.streams;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CutTest
    extends MainTest
{
    @Test
    public void cutObjectsListInTheMiddle()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count,
            count/6 + nextInt(count/4),
            count - count/6 - nextInt(count/4));
    }
    @Test
    public void cutObjectsListInTheMiddleEmpty()
    {
        int count = 1000 + nextInt(1000);
        int start = count/6 + nextInt(count/4);
        cutObjectsList(count,
            start,
            start);
    }
    @Test
    public void cutObjectsListInTheMiddleOne()
    {
        int count = 1000 + nextInt(1000);
        int start = count/6 + nextInt(count/4);
        cutObjectsList(count,
            start,
            start + 1);
    }
    @Test
    public void cutObjectsListTail()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count,
            -(count/6 + nextInt(count/4)),
            count - count/6 - nextInt(count/4));
    }
    @Test
    public void cutObjectsListHead()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count,
            count/6 + nextInt(count/4),
            count + count/6 + nextInt(count/4));
    }
    @Test
    public void cutObjectsListEmpty()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count, 0, 0);
    }
    @Test
    public void cutObjectsListLessThenTail()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count,
            -count,
            -(count/6 + nextInt(count/4)));
    }
    @Test
    public void cutObjectsListMoreThenHead()
    {
        int count = 1000 + nextInt(1000);
        cutObjectsList(count,
            count + count/6 + nextInt(count/4),
            count + count);
    }
    @Test
    public void cutObjectsListWrongStartValue()
    {
        int count = 1000 + nextInt(1000);
        try
        {
            Streams.from(nextList(count))
                   .cut(hascodeObjectComparator, count * 2, count - count/6 - nextInt(count/4))
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
        fail("IndexOutOfBoundsException must be thrown!");
    }
    @Test
    public void cutObjectsListWrongEndValue()
    {
        int count = 1000 + nextInt(1000);
        try
        {
            Streams.from(nextList(count))
                   .cut(hascodeObjectComparator, count/6 + nextInt(count/4), -count)
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
        fail("IndexOutOfBoundsException must be thrown!");
    }

    private void cutObjectsList(int count, int start, int end)
    {
        List<Object> source = nextList(count);
        Collections.sort(source, hascodeObjectComparator);
        int s = start < 0 ? 0 : start;
        int e = end > count ? count : end;
        Object[] result = new Object[s >= count ? 0 : end < 0 ? 0 : e-s];
        for(int i=s; i<e; i++)
        {
            result[i-s] = source.get(i);
        }
        int sum1 = 0;
        for(Object it: result)
        {
            sum1 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        int sum2 = 0;
        for(Object it: Streams.from(source)
                              .cut(hascodeObjectComparator, start, end)
                              .turn(To.list()))
        {
            sum2 += (it.hashCode() + "_" + it.hashCode()*2 + "_" + it.hashCode()/2 + "_" + it.hashCode()*it.hashCode()).hashCode();
        }
        assertEquals("Sums must be equals!", sum1, sum2);
    }
}