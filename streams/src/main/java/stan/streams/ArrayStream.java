package stan.streams;

import java.util.Arrays;
import java.util.List;

import stan.streams.functions.Consumer;

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
    public List<T> list()
    {
        return Arrays.asList(raw);
    }
}