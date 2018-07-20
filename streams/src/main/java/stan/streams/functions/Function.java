package stan.streams.functions;

public interface Function<S, D>
{
    D apply(S d);
}