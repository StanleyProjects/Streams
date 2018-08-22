package stan.streams.test;

import org.junit.Test;

import java.util.Collections;

import stan.streams.Streams;
import stan.streams.utils.MainTest;

import static org.junit.Assert.fail;

public class StreamsTest
    extends MainTest
{
    @Test
    public void fromNotEmptyListTest()
    {
        try
        {
            Streams.from(nextList());
        }
        catch(Exception e)
        {
            fail("Should work but: " + e.getMessage());
        }
    }
    @Test
    public void fromEmptyListTest()
    {
        try
        {
            Streams.from(Collections.emptyList());
        }
        catch(Exception e)
        {
            fail("Should work but: " + e.getMessage());
        }
    }
    @Test
    public void fromNullTest()
    {
        try
        {
            Streams.from(null);
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