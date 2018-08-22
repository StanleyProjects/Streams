package stan.streams.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import stan.streams.Streams;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class FirstLastTest
    extends MainTest
{
    static private final Comparator comparator = new Comparator()
    {
        public int compare(Object o1, Object o2)
        {
            return o1.hashCode() > o2.hashCode() ? 1 : o1.hashCode() < o2.hashCode() ? -1 : 0;
        }
    };

    @Test
    public void findFirstFromRandomNotEmptyListTest()
    {
        findFirstFromList(nextList());
    }
    @Test
    public void findFirstFromEmptyListTest()
    {
        findFirstFromList(Collections.emptyList());
    }
    @Test
    public void findLastFromRandomNotEmptyListTest()
    {
        findLastFromList(nextList());
    }
    @Test
    public void findLastFromEmptyListTest()
    {
        findLastFromList(Collections.emptyList());
    }

    private void findFirstFromList(List data)
    {
        Object first;
        if(data.isEmpty())
        {
            first = null;
        }
        else if(data.size() == 1)
        {
            first = data.get(0);
        }
        else
        {
            Collections.sort(data, comparator);
            first = data.get(0);
        }
        Assert.assertEquals("First objects must be equals!", first, Streams.from(data).first(comparator));
    }
    private void findLastFromList(List data)
    {
        Object first;
        if(data.isEmpty())
        {
            first = null;
        }
        else if(data.size() == 1)
        {
            first = data.get(0);
        }
        else
        {
            Collections.sort(data, comparator);
            first = data.get(data.size() - 1);
        }
        assertEquals("Last objects must be equals!", first, Streams.from(data).last(comparator));
    }
}