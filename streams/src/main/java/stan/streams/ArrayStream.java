package stan.streams;

import java.util.Arrays;
import java.util.Comparator;

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
    public Stream<T> cut(Comparator<T> comparator, int start, int end)
    {
        if(start > end)
        {
            throw new IndexOutOfBoundsException();
        }
        if(start < 0)
        {
            start = 0;
        }
        if(end > raw.length)
        {
            end = raw.length;
        }
        if(start >= raw.length
            || end < 0)
        {
            return new ArrayStream<T>((T[])new Object[0]);
        }
        Arrays.sort(raw, comparator);
        if(start == 0
            && end == raw.length)
        {
            return this;
        }
        T[] newRaw = (T[])new Object[end - start];
        System.arraycopy(raw, start, newRaw, 0, end - start);
        return new ArrayStream<T>(newRaw);
    }
    public Stream<T> tail(Comparator<T> comparator, int count)
    {
        if(count < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        Arrays.sort(raw, comparator);
        if(count > raw.length)
        {
            return this;
        }
        T[] newRaw = (T[])new Object[count];
        System.arraycopy(raw, 0, newRaw, 0, count);
        return new ArrayStream<T>(newRaw);
    }
    public Stream<T> head(Comparator<T> comparator, int count)
    {
        if(count < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        if(count > raw.length)
        {
            return this;
        }
        Arrays.sort(raw, comparator);
        T[] newRaw = (T[])new Object[count];
        System.arraycopy(raw, raw.length - count, newRaw, 0, count);
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