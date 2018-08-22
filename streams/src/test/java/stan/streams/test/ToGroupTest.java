package stan.streams.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stan.streams.Streams;
import stan.streams.To;
import stan.streams.functions.Function;
import stan.streams.utils.MainTest;

import static org.junit.Assert.assertEquals;

public class ToGroupTest
    extends MainTest
{
    private final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
        "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Test
    public void turnStringStreamToGroupRandomNotEmptyListTest()
    {
        turnStringStreamToGroup(nextAlphabeticStringList());
    }
    @Test
    public void turnStringStreamToGroupEmptyListTest()
    {
        turnStringStreamToGroup(Collections.<String>emptyList());
    }
    @Test
    public void turnIntegerStreamToGroupRandomNotEmptyListTest()
    {
        turnIntegerStreamToGroup(nextIntegerList());
    }
    @Test
    public void turnIntegerStreamToGroupEmptyListTest()
    {
        turnIntegerStreamToGroup(Collections.<Integer>emptyList());
    }
    @Test
    public void turnObjectStreamToGroupRandomNotEmptyListTest()
    {
        turnObjectStreamToGroup(nextList());
    }
    @Test
    public void turnObjectStreamToGroupEmptyListTest()
    {
        turnObjectStreamToGroup(Collections.emptyList());
    }

    private void turnStringStreamToGroup(List<String> data)
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
            List<String> value = group1.get(key);
            res1 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(String it: value)
            {
                res1 += (it.length() +"_"+ it.hashCode()).hashCode();
            }
        }
        int res2 = 0;
        Map<String, List<String>> group2 = Streams.from(data).turn(To.group(new Function<String, String>()
        {
            public String apply(String it)
            {
                return it.substring(0, 1);
            }
        }));
        for(String key: group2.keySet())
        {
            List<String> value = group2.get(key);
            res2 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(String it: value)
            {
                res2 += (it.length() +"_"+ it.hashCode()).hashCode();
            }
        }
        assertEquals("Results must be equals!", res1, res2);
    }
    private void turnIntegerStreamToGroup(List<Integer> data)
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
            List<Integer> value = group1.get(key);
            res1 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(int it: value)
            {
                res1 += it;
            }
        }
        Map<String, List<Integer>> group2 = Streams.from(data).turn(To.group(new Function<Integer, String>()
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
        }));
        int res2 = 0;
        for(String key: group2.keySet())
        {
            List<Integer> value = group2.get(key);
            res2 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(int it: value)
            {
                res2 += it;
            }
        }
        assertEquals("Results must be equals!", res1, res2);
    }
    private void turnObjectStreamToGroup(List<Object> data)
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
            List value = group1.get(key);
            res1 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(Object it: value)
            {
                res1 += it.hashCode();
            }
        }
        int res2 = 0;
        Map<String, List<Object>> group2 = Streams.from(data).turn(To.group(new Function<Object, String>()
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
        }));
        for(String key: group2.keySet())
        {
            List value = group2.get(key);
            res2 += (value.size() +"_"+ value.hashCode()).hashCode();
            for(Object it: value)
            {
                res2 += it.hashCode();
            }
        }
        assertEquals("Results must be equals!", res1, res2);
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