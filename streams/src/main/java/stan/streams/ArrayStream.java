package stan.streams;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Consumer;
import stan.streams.functions.Function;
import stan.streams.functions.Predicate;

final class ArrayStream<T>
    implements Stream<T>
{
    private final T[] raw;

    ArrayStream(T[] raw)
    {
        if(raw == null)
        {
            throw new IllegalArgumentException("Array must be exist!");
        }
        this.raw = raw;
    }

    public Stream<T> foreach(Consumer<T> consumer)
    {
        for(T t: raw)
        {
            consumer.accept(t);
        }
        return this;
    }
    public <D> Stream<D> map(Function<T, D> function)
    {
        D[] newRaw = (D[])new Object[raw.length];
        for(int i=0; i<raw.length; i++)
        {
            newRaw[i] = function.apply(raw[i]);
        }
        return new ArrayStream<D>(newRaw);
    }
    public Stream<T> filter(Predicate<T> predicate)
    {
        int l = 0;
        for(T t: raw)
        {
            if(predicate.test(t))
            {
                l++;
            }
        }
        T[] newRaw = (T[])new Object[l];
        for(T t: raw)
        {
            if(predicate.test(t))
            {
                newRaw[--l] = t;
            }
        }
        return new ArrayStream<T>(newRaw);
    }
    public <R> R turn(R r, BiConsumer<R, T> consumer)
    {
        for(T t: raw)
        {
            consumer.accept(r, t);
        }
        return r;
    }
    public <R> R turn(Collector<R, T> collector)
    {
        return turn(collector.supplier(), collector.accumulator());
    }
}