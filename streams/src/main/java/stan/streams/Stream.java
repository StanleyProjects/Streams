package stan.streams;

import java.util.List;

import stan.streams.functions.Consumer;

public interface Stream<T>
{
    Stream<T> foreach(Consumer<T> consumer);
    List<T> list();
}