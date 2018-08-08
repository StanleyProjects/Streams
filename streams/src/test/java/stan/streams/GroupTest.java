package stan.streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Function;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class GroupTest
    extends MainTest
{
    private final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
        "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Test
    public void groupStringStreamFromRandomNotEmptyListTest()
    {
        groupStringStream(nextAlphabeticStringList());
    }
    @Test
    public void groupStringStreamFromEmptyListTest()
    {
        groupStringStream(Collections.<String>emptyList());
    }
    @Test
    public void groupIntegerStreamFromRandomNotEmptyListTest()
    {
        groupIntegerStream(nextIntegerList());
    }
    @Test
    public void groupIntegerStreamFromEmptyListTest()
    {
        groupIntegerStream(Collections.<Integer>emptyList());
    }
    @Test
    public void groupObjectStreamFromRandomNotEmptyListTest()
    {
        groupObjectStream(nextList());
    }
    @Test
    public void groupObjectStreamFromEmptyListTest()
    {
        groupObjectStream(Collections.emptyList());
    }

    private void groupStringStream(List<String> data)
    {
        Map<String, List<String>> group1 = new HashMap<String, List<String>>();
        for(String it: data)
        {
            String key = it.substring(0, 1);
            List<String> list = group1.get(key);
            if(list == null) list = new ArrayList<String>();
            list.add(it);
            group1.put(key, list);
        }
        int res1 = 0;
        for(String key: group1.keySet())
        {
            res1 += key.hashCode();
            for(String it: group1.get(key))
            {
                res1 += (it.length() +"_"+ it.hashCode()).hashCode();
            }
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).group(new Function<String, String>()
        {
            public String apply(String it)
            {
                return it.substring(0, 1);
            }
        }).turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Pair<String, Stream<String>>>()
        {
            public void accept(AtomicInteger r, Pair<String, Stream<String>> it)
            {
                r.addAndGet(it.first.hashCode());
                r.addAndGet(it.second.turn(new AtomicInteger(), new BiConsumer<AtomicInteger, String>()
                {
                    public void accept(AtomicInteger r, String it)
                    {
                        r.addAndGet((it.length() +"_"+ it.hashCode()).hashCode());
                    }
                }).intValue());
            }
        }).intValue());
    }
    private void groupIntegerStream(List<Integer> data)
    {
        Map<String, List<Integer>> group1= new HashMap<String, List<Integer>>();
        for(int it: data)
        {
            int tmp = it;
            String key = "10";
            while(tmp > 10)
            {
                key = String.valueOf(tmp);
                tmp /= 10;
            }
            List<Integer> list = group1.get(key);
            if(list == null) list = new ArrayList<Integer>();
            list.add(it);
            group1.put(key, list);
        }
        int res1 = 0;
        for(String key: group1.keySet())
        {
            res1 += key.hashCode();
            for(int it: group1.get(key))
            {
                res1 += it;
            }
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).group(new Function<Integer, String>()
        {
            public String apply(Integer it)
            {
                int tmp = it;
                String key = "10";
                while(tmp > 10)
                {
                    key = String.valueOf(tmp);
                    tmp /= 10;
                }
                return key;
            }
        }).turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Pair<String, Stream<Integer>>>()
        {
            public void accept(AtomicInteger r, Pair<String, Stream<Integer>> it)
            {
                r.addAndGet(it.first.hashCode());
                r.addAndGet(it.second.turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Integer>()
                {
                    public void accept(AtomicInteger r, Integer it)
                    {
                        r.addAndGet(it);
                    }
                }).intValue());
            }
        }).intValue());
    }
    private void groupObjectStream(List<Object> data)
    {
        Map<String, List> group1 = new HashMap<String, List>();
        for(Object it: data)
        {
            int tmp = it.hashCode();
            String key = "10";
            while(tmp > 10)
            {
                key = String.valueOf(tmp);
                tmp /= 10;
            }
            List list = group1.get(key);
            if(list == null) list = new ArrayList<String>();
            list.add(it);
            group1.put(key, list);
        }
        int res1 = 0;
        for(String key: group1.keySet())
        {
            res1 += key.hashCode();
            for(Object it: group1.get(key))
            {
                res1 += it.hashCode();
            }
        }
        assertEquals("Results must be equals!", res1, Streams.from(data).group(new Function<Object, String>()
        {
            public String apply(Object it)
            {
                int tmp = it.hashCode();
                String key = "10";
                while(tmp > 10)
                {
                    key = String.valueOf(tmp);
                    tmp /= 10;
                }
                return key;
            }
        }).turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Pair<String, Stream<Object>>>()
        {
            public void accept(AtomicInteger r, Pair<String, Stream<Object>> it)
            {
                r.addAndGet(it.first.hashCode());
                r.addAndGet(it.second.turn(new AtomicInteger(), new BiConsumer<AtomicInteger, Object>()
                {
                    public void accept(AtomicInteger r, Object it)
                    {
                        r.addAndGet(it.hashCode());
                    }
                }).intValue());
            }
        }).intValue());
    }

    private List<String> nextAlphabeticStringList()
    {
        int count = nextInt(200) + 100;
        List<String> result = new ArrayList<String>(count);
        for(int i=0; i<count; i++)
        {
            StringBuilder builder = new StringBuilder();
            for(int j=0; j<20; j++)
            {
                builder.append(ALPHABET[nextInt(ALPHABET.length)]);
            }
            result.add(builder.toString());
        }
        return result;
    }
}