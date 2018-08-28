package stan.streams;

import java.util.Collection;
import java.util.Map;

public final class Streams
{
    static public <T> Stream<T> from(Collection<T> collection)
    {
        if(collection == null) throw new IllegalArgumentException("Collection must be exist!");
        if(collection.isEmpty()) return empty();
        if(collection.size() == 1) return of(collection.iterator().next());
        return new ArrayStream<T>((T[])collection.toArray());
    }
    static public <K, V> Stream<Pair<K, V>> from(Map<K, V> map)
    {
        if(map == null) throw new IllegalArgumentException("Collection must be exist!");
        if(map.isEmpty()) return empty();
        if(map.size() == 1)
        {
            K key = map.keySet().iterator().next();
            return of(new Pair<K, V>(key, map.get(key)));
        }
        Pair<K, V>[] array = new Pair[map.size()];
        int i=0;
        for(K key: map.keySet()) array[i++] = new Pair<K, V>(key, map.get(key));
        return new ArrayStream<Pair<K, V>>(array);
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
    static private Stream<Character> from(char[] items)
    {
        Character[] raw = new Character[items.length];
        for(int i=0; i<items.length; i++) raw[i] = items[i];
        return new ArrayStream<Character>(raw);
    }
    static public Stream<Character> from(String string)
    {
        if(string == null) throw new IllegalArgumentException("String must be exist!");
        char[] items = string.toCharArray();
        if(items.length == 0) return empty();
        if(items.length == 1) return of(items[0]);
        return from(items);
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
    static public Stream<Integer> range(int count)
    {
        return range(0, count);
    }
    static public Stream<Integer> range(int from, int count)
    {
        if(count < 0) throw new IllegalArgumentException("Count must be not negative!");
        Integer[] raw = new Integer[count];
        for(int i=0; i<count; i++) raw[i] = from + i;
        return new ArrayStream<Integer>(raw);
    }

    private Streams()
    {}
}