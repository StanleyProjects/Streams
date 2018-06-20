package stan.streams.functions;

public interface Consumer<T>
{
    void accept(T t);
}