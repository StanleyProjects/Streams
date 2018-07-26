package stan.streams;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import stan.streams.functions.BiConsumer;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class TurnCollectorTest
    extends MainTest
{
    @Test
    public void turnStringListToIntegerRandomNotEmptyListTest()
    {
        turnStringListToIntegerTest(nextStringList());
    }
    @Test
    public void turnStringListToIntegerEmptyListTest()
    {
        turnStringListToIntegerTest(Collections.<String>emptyList());
    }
    @Test
    public void turnIntegerToStringRandomNotEmptyListTest()
    {
        turnIntegerListToStringTest(nextIntegerList());
    }
    @Test
    public void turnIntegerToStringEmptyListTest()
    {
        turnIntegerListToStringTest(Collections.<Integer>emptyList());
    }
    @Test
    public void turnObjectListToStringRandomNotEmptyListTest()
    {
        turnObjectListToStringTest(nextList());
    }
    @Test
    public void turnObjectListToStringEmptyListTest()
    {
        turnObjectListToStringTest(Collections.emptyList());
    }

    private void turnStringListToIntegerTest(List<String> data)
    {
        int res1 = 0;
        for(String it: data)
        {
            res1 += it.length();
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).turn(new Collector<Sum, String>()
        {
            public Sum supplier()
            {
                return new Sum();
            }
            public BiConsumer<Sum, String> accumulator()
            {
                return new BiConsumer<Sum, String>()
                {
                    public void accept(Sum r, String it)
                    {
                        r.add(it.length());
                    }
                };
            }
        }).value());
    }
    private void turnIntegerListToStringTest(List<Integer> data)
    {
        String res1 = "";
        for(int it: data)
        {
            res1 += it + "_" + it*2 + "_" + it/2 + "_" + it*it;
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).turn(new Collector<StringBuilder, Integer>()
        {
            public StringBuilder supplier()
            {
                return new StringBuilder();
            }
            public BiConsumer<StringBuilder, Integer> accumulator()
            {
                return new BiConsumer<StringBuilder, Integer>()
                {
                    public void accept(StringBuilder r, Integer it)
                    {
                        r.append(it + "_" + it*2 + "_" + it/2 + "_" + it*it);
                    }
                };
            }
        }).toString());
    }
    private void turnObjectListToStringTest(List<Object> data)
    {
        String res1 = "";
        for(Object it: data)
        {
            res1 += it + "_" + it.hashCode() + "_" + it.toString();
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).turn(new Collector<StringBuilder, Object>()
        {
            public StringBuilder supplier()
            {
                return new StringBuilder();
            }
            public BiConsumer<StringBuilder, Object> accumulator()
            {
                return new BiConsumer<StringBuilder, Object>()
                {
                    public void accept(StringBuilder r, Object it)
                    {
                        r.append(it + "_" + it.hashCode() + "_" + it.toString());
                    }
                };
            }
        }).toString());
    }
}