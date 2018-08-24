package stan.streams.test;

import org.junit.Test;

import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreateTest
    extends MainTest
{
    @Test
    public void createFromArrayTest()
    {
        Object[] source = nextArray();
        int res1 = 0;
        for(Object it: source) res1 += it.hashCode() + it.hashCode()*2
            + it.hashCode()/2 + it.hashCode()*it.hashCode();
        int res2 = 0;
        for(Object it: Streams.from(source).turn(To.list()))
            res2 += it.hashCode() + it.hashCode()*2+ it.hashCode()/2 + it.hashCode()*it.hashCode();
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void createFromEmptyArrayTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.from(new Object[0]).turn(To.list()).isEmpty());
    }
    @Test
    public void createFromNullArrayTest()
    {
        try
        {
            Streams.from((Object[])null);
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
    @Test
    public void createFromEmptyTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.empty().turn(To.list()).isEmpty());
    }
    @Test
    public void createFromItemsTest()
    {
        Object[] source = new Object[]{nextObject(), nextObject(), nextObject(), nextObject()};
        int res1 = 0;
        for(Object it: source) res1 += it.hashCode() + it.hashCode()*2
            + it.hashCode()/2 + it.hashCode()*it.hashCode();
        int res2 = 0;
        for(Object it: Streams.of(source[0],
            source[1],
            source[2],
            source[3]).turn(To.list()))
            res2 += it.hashCode() + it.hashCode()*2+ it.hashCode()/2 + it.hashCode()*it.hashCode();
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void createFromNullTwoItemsTest()
    {
        for(Object it: Streams.of(null, (Object)null).turn(To.list())) assertNull("Result must be null!", it);
    }
    @Test
    public void createFromNullThreeItemsTest()
    {
        for(Object it: Streams.of(null, null, null).turn(To.list())) assertNull("Result must be null!", it);
    }
    @Test
    public void createFromSingleItemTest()
    {
        Object source = nextObject();
        List result = Streams.of(source).turn(To.list());
        assertEquals("Item must be exist and only one!", result.size(), 1);
        assertEquals("Results must be equals!",
            source.hashCode(),
            result.get(0).hashCode());
    }
    @Test
    public void createFromNullSingleItemTest()
    {
        List result = Streams.of(null).turn(To.list());
        assertEquals("Item must be exist and only one!", result.size(), 1);
        assertNull("Result must be null!", result.get(0));
    }
    @Test
    public void createFromSingleItemAndNotEmptyArrayTest()
    {
        Object[] source = new Object[]{nextObject(), nextObject(), nextObject(), nextObject()};
        int res1 = 0;
        for(Object it: source) res1 += it.hashCode() + it.hashCode()*2
            + it.hashCode()/2 + it.hashCode()*it.hashCode();
        int res2 = 0;
        for(Object it: Streams.of(source[0],
            new Object[]{source[1],
            source[2],
            source[3]}).turn(To.list()))
            res2 += it.hashCode() + it.hashCode()*2+ it.hashCode()/2 + it.hashCode()*it.hashCode();
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void createFromSingleItemAndEmptyArrayTest()
    {
        Object source = nextObject();
        List result = Streams.of(source, new Object[0]).turn(To.list());
        assertEquals("Item must be exist and only one!", result.size(), 1);
        assertEquals("Results must be equals!",
            source.hashCode(),
            result.get(0).hashCode());
    }
    @Test
    public void createFromSingleItemAndNullArrayTest()
    {
        try
        {
            Streams.of(nextObject(), (Object[])null);
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
    @Test
    public void createFromNullSingleItemAndNullArrayTest()
    {
        try
        {
            Streams.of(null, (Object[])null);
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