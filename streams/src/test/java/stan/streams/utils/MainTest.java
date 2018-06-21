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
            data.add((Object)nextString());
        }
        return data;
    }
}