package stan.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Function;

public final class To
{
    static public <I> Collector<List<I>, I> list()
    {
        return new Collector<List<I>, I>()
        {
            public List<I> supplier()
            {
                return new ArrayList<I>();
            }
            public BiConsumer<List<I>, I> accumulator()
            {
                return new BiConsumer<List<I>, I>()
                {
                    public void accept(List<I> r, I it)
                    {
                        r.add(it);
                    }
                };
            }
        };
    }

    static public <K, U, I> Collector<Map<K, U>, I> map(final BiConsumer<Map<K, U>, I> accumulator)
    {
        return new Collector<Map<K, U>, I>()
        {
            public Map<K, U> supplier()
            {
                return new HashMap<K, U>();
            }
            public BiConsumer<Map<K, U>, I> accumulator()
            {
                return accumulator;
            }
        };
    }
    static public <K, I> Collector<Map<K, List<I>>, I> group(final Function<I, K> function)
    {
        return map(new BiConsumer<Map<K, List<I>>, I>()
        {
            public void accept(Map<K, List<I>> r, I it)
            {
                K key = function.apply(it);
                List<I> list = r.get(key);
                if(list == null) list = new ArrayList<I>();
                list.add(it);
                r.put(key, list);
            }
        });
    }
    static public <K, I> Collector<Map<K, I>, I> map(final Function<I, K> function)
    {
        return map(new BiConsumer<Map<K, I>, I>()
        {
            public void accept(Map<K, I> r, I it)
            {
                r.put(function.apply(it), it);
            }
        });
    }
    static public <K, I> Collector<Map<K, I>, Pair<K, I>> map()
    {
        return map(new BiConsumer<Map<K, I>, Pair<K, I>>()
        {
            public void accept(Map<K, I> r, Pair<K, I> it)
            {
                r.put(it.first, it.second);
            }
        });
    }

    static public final Collector<StringBuilder, Character> string = new Collector<StringBuilder, Character>()
    {
        public StringBuilder supplier()
        {
            return new StringBuilder();
        }
        public BiConsumer<StringBuilder, Character> accumulator()
        {
            return new BiConsumer<StringBuilder, Character>()
            {
                public void accept(StringBuilder r, Character it)
                {
                    r.append(it.charValue());
                }
            };
        }
    };

    private To()
    {}
}