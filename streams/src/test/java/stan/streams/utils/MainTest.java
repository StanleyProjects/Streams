package stan.streams.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MainTest
{
    static private final Random random = new Random();

    protected boolean nextBoolean()
    {
        return random.nextBoolean();
    }
    protected final int nextInt()
    {
        return random.nextInt();
    }
    protected final int nextInt(int range)
    {
        return random.nextInt(range);
    }
    protected final long nextLong()
    {
        return random.nextInt();
    }
    protected final double nextDouble()
    {
        return random.nextDouble() + nextInt();
    }
    protected final String nextString()
    {
        byte[] array = new byte[random.nextInt(100) + 50];
        random.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    protected final List<?> nextList()
    {
        return nextList(1000 + nextInt(1000));
    }
    private List<Object> nextList(int count)
    {
        List<Object> data = new ArrayList<Object>();
        for(int i=0; i<count; i++)
        {
            data.add(nextBoolean() ? nextBoolean() ? nextString() : nextInt() : nextDouble());
        }
        return data;
    }

    protected final List<Integer> nextIntegerList()
    {
        return nextIntegerList(1000 + nextInt(1000));
    }
    protected final List<Integer> nextIntegerList(int count)
    {
        List<Integer> data = new ArrayList<Integer>(count);
        for(int i=0; i<count; i++)
        {
            data.add(1000 + nextInt(1000));
        }
        return data;
    }
    protected final List<String> nextStringList()
    {
        return nextStringList(1000 + nextInt(1000));
    }
    protected final List<String> nextStringList(int count)
    {
        List<String> data = new ArrayList<String>(count);
        for(int i=0; i<count; i++)
        {
            data.add(nextString());
        }
        return data;
    }
}