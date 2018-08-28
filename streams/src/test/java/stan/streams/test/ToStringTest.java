package stan.streams.test;

import org.junit.Test;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class ToStringTest
    extends MainTest
{
    @Test
    public void turnToStringFromCharArrayTest()
    {
        char[] source = nextCharArray();
        String result = String.valueOf(source);
        assertEquals("Results must be equals!", result, Streams.from(result).turn(To.string).toString());
    }
    @Test
    public void turnToStringFromEmptyCharArrayTest()
    {
        char[] source = new char[0];
        String result = String.valueOf(source);
        assertEquals("Results must be equals!", result, Streams.from(result).turn(To.string).toString());
    }
}