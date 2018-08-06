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

    static public <K, I> Collector<Map<K, List<I>>, I> group(final Function<I, K> function)
    {
        return new Collector<Map<K, List<I>>, I>()
        {
            private final BiConsumer<Map<K, List<I>>, I> consumer = new BiConsumer<Map<K, List<I>>, I>()
            {
                public void accept(Map<K, List<I>> r, I it)
                {
                    K key = function.apply(it);
                    List<I> list = r.get(key);
                    if(list == null) list = new ArrayList<I>();
                    list.add(it);
                    r.put(key, list);
                }
            };

            public Map<K, List<I>> supplier()
            {
                return new HashMap<K, List<I>>();
            }
            public BiConsumer<Map<K, List<I>>, I> accumulator()
            {
                return consumer;
            }
        };
    }

    private To()
    {}
}