package stan.streams.functions;

public interface BiConsumer<T, U>
{
    void accept(T t, U u);
}