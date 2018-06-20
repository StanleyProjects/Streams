package stan.streams;

import java.util.Arrays;
import java.util.List;

import stan.streams.functions.Consumer;

public class App
{
    static public void main(String[] args)
    {
        List<Integer> result = Streams.from(Arrays.asList(1, 2, 3, 4, 5))
                                      .foreach(new Consumer<Integer>()
                                      {
                                          public void accept(Integer integer)
                                          {
                                              System.out.println("n: " + integer);
                                          }
                                      }).list();
        System.out.println(result);
    }
}