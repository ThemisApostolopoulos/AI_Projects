package cannibalsXMissionaries;

import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    {
        String numOfCannibalsAndMissionaries = args[0];
        String numOfBoatCapacity = args[1];
        String numOfCrosses = args[2];
        State initialState = new State( Integer.parseInt(numOfCannibalsAndMissionaries),  Integer.parseInt(numOfBoatCapacity));  // our initial  state.
        SpaceSearcher searcher = new SpaceSearcher();
        long start = System.currentTimeMillis();
        State terminalState = searcher.AstarAlgorithm(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            int counter = 0;
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                counter ++ ;
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            //check if it found a solution in under K steps
            if(counter > Integer.parseInt(numOfCrosses)) {
                System.out.println("Could not find a solution in " + Integer.parseInt(numOfCrosses) + " steps");
            }else {
                for (State item : path) {
                    item.print();
                }
                System.out.println();
                System.out.println("Search time:" + (double) (end - start) / 1000 + " sec.");  // total time of searching in seconds.
            }
        }
    }
}
