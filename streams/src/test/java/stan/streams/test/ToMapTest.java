package stan.streams.test;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ToMapTest
    extends MainTest
{
    @Test
    public void turnToMapFromPairArrayTest()
    {
        Map<String, Integer> source = new HashMap<String, Integer>();
        int count = nextInt(500) + 500;
        for(int i=0; i<count; i++) source.put(nextString(), nextInt());
        int res1 = 0;
        for(String key: source.keySet())
        {
            int value = source.get(key);
            res1 += key.hashCode() + key.hashCode() * 2 + key.hashCode() / 2
                + key.hashCode() * value + value + value * 2 + value / 2;
        }
        int res2 = 0;
        Map<String, Integer> result = Streams.from(source).turn(To.<String, Integer>map());
        for(String key: result.keySet())
        {
            int value = result.get(key);
            res2 += key.hashCode() + key.hashCode() * 2 + key.hashCode() / 2
                + key.hashCode() * value + value + value * 2 + value / 2;
        }
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void turnToStringFromEmptyPairArrayTest()
    {
        assertTrue("Result of empty stream must be a empty map!",
            Streams.from(Collections.emptyMap()).turn(To.map()).isEmpty());
    }
}