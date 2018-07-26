package stan.streams;

import stan.streams.functions.BiConsumer;

public interface Collector<R, T>
{
    R supplier();
    BiConsumer<R, T> accumulator();
}