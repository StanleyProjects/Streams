package stan.streams;

import java.util.Collection;

public final class Streams
{
    static public <T> Stream<T> from(Collection<T> collection)
    {
        return new ArrayStream<T>((T[])collection.toArray());
    }

    private Streams()
    {}
}