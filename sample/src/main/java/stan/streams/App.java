package stan.streams;

import stan.streams.sample.ImplementBasicFunctions;
import stan.streams.sample.ImplementCollectFeature;
import stan.streams.sample.SortOutAndGrouping;

public class App
{
    static public void main(String[] args)
    {
        System.out.println("\tStreams demonstration");
        System.out.println("\n");
        ImplementBasicFunctions.sample();
        System.out.println("\n");
        ImplementCollectFeature.sample();
        System.out.println("\n");
        SortOutAndGrouping.sample();
    }
}