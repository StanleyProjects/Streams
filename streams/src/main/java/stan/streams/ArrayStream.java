package stan.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Consumer;
import stan.streams.functions.Function;

final class ArrayStream<T>
    implements Stream<T>
{
    private final T[] raw;

    ArrayStream(T[] raw)
    {
        this.raw = raw;
    }

    public Stream<T> foreach(Consumer<T> consumer)
    {
        for(T t: raw) consumer.accept(t);
        return this;
    }
    public <D> Stream<D> map(Function<T, D> function)
    {
        D[] newRaw = (D[])new Object[raw.length];
        for(int i=0; i<raw.length; i++) newRaw[i] = function.apply(raw[i]);
        return new ArrayStream<D>(newRaw);
    }
    public Stream<T> filter(Function<T, Boolean> function)
    {
        int l = 0;
        for(T t: raw) if(function.apply(t)) l++;
        T[] newRaw = (T[])new Object[l];
        for(T t: raw) if(function.apply(t)) newRaw[--l] = t;
        return new ArrayStream<T>(newRaw);
    }
    public Stream<T> cut(Comparator<T> comparator, int beginIndex, int endIndex)
    {
        if(beginIndex > endIndex) throw new IndexOutOfBoundsException();
        if(beginIndex < 0) beginIndex = 0;
        if(endIndex > raw.length) endIndex = raw.length;
        if(beginIndex >= raw.length
            || endIndex < 0) return new ArrayStream<T>((T[])new Object[0]);
        if(raw.length > 1) Arrays.sort(raw, comparator);
        if(beginIndex == 0
            && endIndex == raw.length) return this;
        T[] newRaw = (T[])new Object[endIndex - beginIndex];
        System.arraycopy(raw, beginIndex, newRaw, 0, endIndex - beginIndex);
        return new ArrayStream<T>(newRaw);
    }
    public Stream<T> tail(Comparator<T> comparator, int count)
    {
        if(count < 0) throw new IndexOutOfBoundsException();
        Arrays.sort(raw, comparator);
        if(count > raw.length) return this;
        T[] newRaw = (T[])new Object[count];
        System.arraycopy(raw, 0, newRaw, 0, count);
        return new ArrayStream<T>(newRaw);
    }
    public Stream<T> head(Comparator<T> comparator, int count)
    {
        if(count < 0) throw new IndexOutOfBoundsException();
        if(count > raw.length) return this;
        if(raw.length > 1) Arrays.sort(raw, comparator);
        T[] newRaw = (T[])new Object[count];
        System.arraycopy(raw, raw.length - count, newRaw, 0, count);
        return new ArrayStream<T>(newRaw);
    }
    public <K> Stream<Pair<K, Stream<T>>> group(Function<T, K> function)
    {
        Map<K, List<T>> group = turn(To.group(function));
        List<Pair<K, Stream<T>>> result = new ArrayList<Pair<K, Stream<T>>>(raw.length);
        for(K key: group.keySet()) result.add(new Pair<K, Stream<T>>(key,
            new ArrayStream<T>((T[])group.get(key).toArray())));
        return Streams.from(result);
    }
    public <R> R turn(R r, BiConsumer<R, T> consumer)
    {
        for(T t: raw) consumer.accept(r, t);
        return r;
    }
    public <R> R turn(Collector<R, T> collector)
    {
        return turn(collector.supplier(), collector.accumulator());
    }
    public T first(Comparator<T> comparator)
    {
        if(raw.length == 0) return null;
        if(raw.length > 1) Arrays.sort(raw, comparator);
        return raw[0];
    }
    public T last(Comparator<T> comparator)
    {
        if(raw.length == 0) return null;
        if(raw.length > 1) Arrays.sort(raw, comparator);
        return raw[raw.length - 1];
    }
}