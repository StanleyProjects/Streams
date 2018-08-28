package stan.streams.test;

import org.junit.Test;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FromStringTest
    extends MainTest
{
    @Test
    public void createFromStringTest()
    {
        String source = nextString();
        char[] characters = source.toCharArray();
        int res1 = 0;
        for(char it: characters) res1 += it + it*2 + it/2 + it*it;
        int res2 = 0;
        for(Character it: Streams.from(source).turn(To.<Character>list()))
            res2 += it + it*2 + it/2 + it*it;
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void createFromEmptyStringTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.from("").turn(To.<Character>list()).isEmpty());
    }
    @Test
    public void createFromNullStringTest()
    {
        try
        {
            Streams.from((String)null);
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