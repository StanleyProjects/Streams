package stan.streams;

import java.util.List;

import stan.streams.functions.Consumer;
import stan.streams.functions.Function;

public interface Stream<T>
{
    Stream<T> foreach(Consumer<T> consumer);
    <D> Stream<D> map(Function<T, D> consumer);
    List<T> list();
}