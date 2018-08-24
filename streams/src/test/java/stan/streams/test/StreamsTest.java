package stan.streams.test;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StreamsTest
    extends MainTest
{
    @Test
    public void fromNotEmptyListTest()
    {
        List<Object> source = nextList();
        int res1 = 0;
        for(Object it: source) res1 += it.hashCode() + it.hashCode()*2
            + it.hashCode()/2 + it.hashCode()*it.hashCode();
        int res2 = 0;
        for(Object it: Streams.from(source).turn(To.list()))
            res2 += it.hashCode() + it.hashCode()*2 + it.hashCode()/2 + it.hashCode()*it.hashCode();
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void fromNotEmptySingleItemListTest()
    {
        Object source = nextObject();
        List result = Streams.from(Collections.singletonList(source)).turn(To.list());
        assertEquals("Item must be exist and only one!", result.size(), 1);
        assertEquals("Results must be equals!",
            source.hashCode(),
            result.get(0).hashCode());
    }
    @Test
    public void fromEmptyListTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.from(Collections.emptyList()).turn(To.list()).isEmpty());
    }
    @Test
    public void fromNullTest()
    {
        try
        {
            Streams.from((Collection)null);
        }
        catch(IllegalArgumentException e)
        {
            return;
        }
        catch(Exception e)
        {
            fail("Should work but: " + e.getMessage());
        }
        fail("IllegalArgumentException must be thrown!");
    }
}