package stan.streams;

import java.util.Arrays;
import java.util.List;

import stan.streams.functions.Consumer;
import stan.streams.functions.Function;

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
    public List<T> list()
    {
        return Arrays.asList(raw);
    }
}