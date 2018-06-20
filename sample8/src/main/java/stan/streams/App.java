package stan.streams;

import java.util.Arrays;
import java.util.List;

public class App
{
    static public void main(String[] args)
    {
        List<Integer> result = Streams.from(Arrays.asList(1, 2, 3, 4, 5))
                                      .foreach(it -> System.out.println("n: " + it))
                                      .list();
        System.out.println(result);
    }
}