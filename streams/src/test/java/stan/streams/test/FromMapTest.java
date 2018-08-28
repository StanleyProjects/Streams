package stan.streams.test;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import stan.streams.Pair;
import stan.streams.Streams;
import stan.streams.To;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FromMapTest
    extends MainTest
{
    @Test
    public void createFromMapTest()
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
        for(Pair<String, Integer> it: Streams.from(source).turn(To.<Pair<String, Integer>>list()))
            res2 += it.first.hashCode() + it.first.hashCode()*2 + it.first.hashCode()/2
                + it.first.hashCode()*it.second + it.second + it.second*2 + it.second/2;
        assertEquals("Results must be equals!", res1, res2);
    }
    @Test
    public void createFromEmptyMapTest()
    {
        assertTrue("Result of empty stream must be a empty list!",
            Streams.from(Collections.emptyMap()).turn(To.<Pair<Object, Object>>list()).isEmpty());
    }
    @Test
    public void createFromNullMapTest()
    {
        try
        {
            Streams.from((Map)null);
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