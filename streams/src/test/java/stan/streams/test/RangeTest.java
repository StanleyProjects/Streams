package stan.streams.test;

import org.junit.Test;

import java.util.List;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RangeTest
    extends MainTest
{
    @Test
    public void createFromRangeFromZeroToNumberTest()
    {
        int count = nextInt(100) + 100;
        int[] source = new int[count];
        for(int i=0; i<count; i++) source[i] = i;
        List<Integer> result = Streams.range(count).turn(To.<Integer>list());
        for(int i=0; i<count; i++) assertEquals("Results must be equals!", Integer.valueOf(source[i]), result.get(i));
    }
    @Test
    public void createFromRangeFromNumberToNumberTest()
    {
        int count = nextInt(100) + 100;
        int shift = nextInt(50) + 25;
        int[] source = new int[count];
        for(int i=0; i<count; i++) source[i] = i + shift;
        List<Integer> result = Streams.range(shift, count).turn(To.<Integer>list());
        for(int i=0; i<count; i++) assertEquals("Results must be equals!", Integer.valueOf(source[i]), result.get(i));
    }
    @Test
    public void createFromEmptyRangeTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.range(0).turn(To.<Integer>list()).isEmpty());
        assertTrue("Result of empty stream must be a empty list!",
            Streams.range(nextInt(), 0).turn(To.<Integer>list()).isEmpty());
    }
    @Test
    public void createFromRangeFromZeroToNegativeNumberTest()
    {
        try
        {
            Streams.range((nextInt(100) + 1)*-1);
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
    public void createFromRangeFromNumberToNegativeNumberTest()
    {
        try
        {
            Streams.range(nextInt(100) + 1, (nextInt(100) + 1)*-1);
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