package stan.streams.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public abstract class MainTest
{
    static private final Random random = new Random();
    static protected final Comparator<Object> hascodeObjectComparator = new Comparator<Object>()
    {
        public int compare(Object o1, Object o2)
        {
            return o1.hashCode() > o2.hashCode() ? 1 : o1.hashCode() < o2.hashCode() ? -1 : 0;
        }
    };

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
    protected final char nextChar()
    {
        return (char)random.nextInt(Character.MAX_VALUE);
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
    protected final Object nextObject()
    {
        return nextBoolean()
            ? nextBoolean()
                ? nextBoolean()
                    ? nextString()
                    : nextInt()
                : nextDouble()
            : nextBoolean();
    }

    protected final char[] nextCharArray()
    {
        return nextCharArray(1000 + nextInt(1000));
    }
    private char[] nextCharArray(int count)
    {
        char[] result = new char[count];
        for(int i=0; i<count; i++) result[i] = nextChar();
        return result;
    }
    protected final Object[] nextArray()
    {
        return nextArray(1000 + nextInt(1000));
    }
    protected final Object[] nextArray(int count)
    {
        Object[] result = new Object[count];
        for(int i=0; i<count; i++) result[i] = nextObject();
        return result;
    }
    protected final String[] nextStringArray(int count)
    {
        String[] result = new String[count];
        for(int i=0; i<count; i++) result[i] = nextString();
        return result;
    }
    protected final List<Object> nextList()
    {
        return nextList(1000 + nextInt(1000));
    }
    protected final List<Object> nextList(int count)
    {
        List<Object> data = new ArrayList<Object>();
        for(int i=0; i<count; i++) data.add(nextObject());
        return data;
    }

    protected final List<Character> nextCharacterList()
    {
        return nextCharacterList(1000 + nextInt(1000));
    }
    protected final List<Character> nextCharacterList(int count)
    {
        List<Character> data = new ArrayList<Character>(count);
        for(int i=0; i<count; i++) data.add(nextChar());
        return data;
    }
    protected final List<Integer> nextIntegerList()
    {
        return nextIntegerList(1000 + nextInt(1000));
    }
    protected final List<Integer> nextIntegerList(int count)
    {
        List<Integer> data = new ArrayList<Integer>(count);
        for(int i=0; i<count; i++) data.add(1000 + nextInt(1000));
        return data;
    }
    protected final List<String> nextStringList()
    {
        return nextStringList(1000 + nextInt(1000));
    }
    protected final List<String> nextStringList(int count)
    {
        List<String> data = new ArrayList<String>(count);
        for(int i=0; i<count; i++) data.add(nextString());
        return data;
    }

    protected final class Sum
    {
        private int sum = 0;

        public Sum()
        {}

        public void add(int i)
        {
            sum += i;
        }
        public int value()
        {
            return sum;
        }

        public boolean equals(Object o)
        {
            return o instanceof Sum && equals((Sum)o);
        }
        private boolean equals(Sum that)
        {
            return sum == that.sum;
        }
    }
}