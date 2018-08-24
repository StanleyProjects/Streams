package stan.streams;

import java.util.Comparator;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Consumer;
import stan.streams.functions.Function;
import stan.streams.functions.Predicate;

public interface Stream<T>
{
    Stream<T> foreach(Consumer<T> consumer);
    <D> Stream<D> map(Function<T, D> function);
    Stream<T> filter(Predicate<T> predicate);
    Stream<T> cut(Comparator<T> comparator, int beginIndex, int endIndex);
    Stream<T> tail(Comparator<T> comparator, int count);
    Stream<T> head(Comparator<T> comparator, int count);
    <K> Stream<Pair<K, Stream<T>>> group(Function<T, K> function);
    <R> R turn(R r, BiConsumer<R, T> consumer);
    <R> R turn(Collector<R, T> collector);
    T first(Comparator<T> comparator);
    T last(Comparator<T> comparator);
}