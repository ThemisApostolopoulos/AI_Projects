package cannibalsXMissionaries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher {

    private ArrayList<State> frontier;
    private HashSet<State> closedSet;


    SpaceSearcher() {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }


    /*
    our a star algorithm which returns the final state if it can find it( steps<limit)
     */
    State AstarAlgorithm(State initialState)
    {

        if(initialState.isFinal()) return initialState;
        /*
        we put our initial state in the frontier
         */
        this.frontier.add(initialState);

        /*
        we check if the frontier is empty.
         */
        while(this.frontier.size() > 0)
        {

            /*
            we get the first node out of the frontier.
             */
            State currentState = this.frontier.remove(0);

            /*
            check if the state is final
             */
            if(currentState.isFinal()) return currentState;
            /*
            we put the children of the state in the frontier if there isnt already another state there
            that represents the same state
             */
            if (!this.closedSet.contains(currentState)) {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
            }
            /*
            we sort the frontier based on the heuristic score to get the best state as first
             */
            Collections.sort(this.frontier);

        }
        return null;
    }


}
