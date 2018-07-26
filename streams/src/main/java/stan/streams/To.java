package stan.streams;

import java.util.ArrayList;
import java.util.List;

import stan.streams.functions.BiConsumer;

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

    private To()
    {}
}