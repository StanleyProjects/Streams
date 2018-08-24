package stan.streams;

import java.util.Collection;

public final class Streams
{
    static public <T> Stream<T> from(Collection<T> collection)
    {
        if(collection == null) throw new IllegalArgumentException("Collection must be exist!");
        if(collection.isEmpty()) return empty();
        if(collection.size() == 1) return of(collection.iterator().next());
        return new ArrayStream<T>((T[])collection.toArray());
    }
    static public <T> Stream<T> from(T[] items)
    {
        if(items == null) throw new IllegalArgumentException("Array must be exist!");
        if(items.length == 0) return empty();
        if(items.length == 1) return of(items[0]);
        T[] raw = (T[])new Object[items.length];
        System.arraycopy(items, 0, raw, 0, items.length);
        return new ArrayStream<T>(raw);
    }
    static public <T> Stream<T> of(T it, T... items)
    {
        if(items == null) throw new IllegalArgumentException("Array must be exist!");
        if(items.length == 0) return of(it);
        T[] raw = (T[])new Object[items.length + 1];
        raw[0] = it;
        System.arraycopy(items, 0, raw, 1, items.length);
        return new ArrayStream<T>(raw);
    }
    static public <T> Stream<T> of(T it)
    {
        T[] raw = (T[])new Object[1];
        raw[0] = it;
        return new ArrayStream<T>(raw);
    }
    static public <T> Stream<T> empty()
    {
        return new ArrayStream<T>((T[])new Object[0]);
    }

    private Streams()
    {}
}