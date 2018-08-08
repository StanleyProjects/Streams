package stan.streams;

final class Pair<F, S>
{
    public final F first;
    public final S second;

    public Pair(F first, S second)
    {
        this.first = first;
        this.second = second;
    }

    public String toString()
    {
        return "{"
            + "first=" + (first == null ? "null" : first.toString())
            + "second=" + (second == null ? "null" : second.toString())
            + "}";
    }
    public int hashCode()
    {
        return (first == null ? 0 : first.hashCode())
            + (second == null ? 0 : second.hashCode())*13;
    }
    public boolean equals(Object that)
    {
        return that instanceof Pair && equals((Pair<?, ?>)that);
    }
    private boolean equals(Pair<?, ?> that)
    {
        return (first == that.first || (first != null && first.equals(that.first))) &&
            (second == that.second || (second != null && second.equals(that.second)));
    }
}