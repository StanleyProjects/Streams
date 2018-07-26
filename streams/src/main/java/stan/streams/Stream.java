package stan.streams;

import stan.streams.functions.BiConsumer;
import stan.streams.functions.Consumer;
import stan.streams.functions.Function;
import stan.streams.functions.Predicate;

public interface Stream<T>
{
    Stream<T> foreach(Consumer<T> consumer);
    <D> Stream<D> map(Function<T, D> consumer);
    Stream<T> filter(Predicate<T> predicate);
    <R> R turn(R r, BiConsumer<R, T> consumer);
    <R> R turn(Collector<R, T> collector);
}