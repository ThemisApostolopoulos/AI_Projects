package cannibalsXMissionaries;

import java.util.ArrayList;

public class State implements Comparable<State>{

    /*
    this is our state class where we see where the cannibals and missionaries are at and through various methods we
    generate other states which originate from the "father" state
     */
    private int totalPeople;
    private int cannibalsLeft;
    private int missionariesLeft;
    private int cannibalsRight;
    private int missionariesRight;
    private boolean boatPos; //true if its on the left, false if its on the right
    private State father = null;
    private int heuristicScore; //heuristis score which is calculates using an heuristis function
    private int boatCapacity;
    private int score;   //tree depth

    public State(int n,  int boatCapacity) {
        this.totalPeople = 2*n;
        this.cannibalsLeft = n;
        this.missionariesLeft = n;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.boatCapacity = boatCapacity;
        this.boatPos = true;
        score = 0;
    }


    /*
    "Copy" constructor
     */
    public State(State oldState){
        this.setCannibalsLeft(oldState.getCannibalsLeft());
        this.setCannibalsRight(oldState.getCannibalsRight());
        this.setMissionariesLeft(oldState.getMissionariesLeft());
        this.setMissionariesRight(oldState.getMissionariesRight());
        this.setBoatPos(oldState.getBoatPos());
        this.setBoatCapacity(oldState.getBoatCapacity());
        this.setTotalPeople(oldState.getTotalPeople());
        this.setScore(oldState.getScore());
    }

    /*
    checks if the state is final.By final, we want 0 cannibals and 0 missionaries on the left side
     */
    public boolean isFinal(){
        return (this.cannibalsLeft == 0 && this.missionariesLeft == 0);
    }



    /*
    checks if a state is valid
    (no more cannibals than missionaries on each side of the river, plus on the boat)
     */
    public boolean isValid(){return ((this.cannibalsLeft<= this.missionariesLeft || this.missionariesLeft ==0) && (this.cannibalsRight<=this.missionariesRight || this.missionariesRight ==0) );}

    /*
    check is a boat maneuver is valid and can happen
     */
    public boolean moveBoat(int cannibals, int missionaries , boolean pos){
        //cant move an empty boat
        if(cannibals ==0 && missionaries ==0) return false;
        //cant move the boat if the people on board are more than the valid capacity of the boat
        if(cannibals + missionaries > boatCapacity)return false;
        if(cannibals>missionaries && missionaries !=0) return false;
        //if the boat's on the left side, move it to the right
        if(pos){
            this.missionariesLeft -= missionaries;
            this.cannibalsLeft -= cannibals;
            this.missionariesRight += missionaries;
            this.cannibalsRight += cannibals;
            boatPos = false;
        }else{
            this.missionariesRight -= missionaries;
            this.cannibalsRight -= cannibals;
            this.missionariesLeft += missionaries;
            this.cannibalsLeft += cannibals;
            boatPos = true;
        }
        /*
        if with that particular boat maneuver we broke a rule of the "puzzle"
        return false so that maneuver wont happen
         */
        if(!isValid()){
            return false;
        }
        return true;
    }


    /*
    method to generate the possible children of the father state
     */
    public ArrayList<State> getChildren(){

        ArrayList<State> children = new ArrayList<>();

        if(boatPos){
            for(int i=0; i<= cannibalsLeft ; i++) {
                for (int j = 0; j <= missionariesLeft; j++) {
                    State child = new State(this);
                    if(child.moveBoat(i, j, this.boatPos)){
                        /*
                        add score tells us the width of the tree so by going for example from state 0, which is the root
                        of the tree to state 1, one level below, now the width is 1
                         */
                        child.addScore();
                        child.countRemainingPeople();
                        child.setFather(this);
                        children.add(child);
                    }
                }
            }
        }else{
            for(int i=0; i<= cannibalsRight ; i++) {
                if(missionariesRight == 0){
                    State child = new State(this);
                    if(child.moveBoat(i, 0, this.boatPos)){
                        child.addScore();
                        child.countRemainingPeople();
                        child.setFather(this);
                        children.add(child);
                    }
                }else{
                    for (int j = 0; j <= missionariesRight; j++) {
                        State child = new State(this);
                        if(child.moveBoat(i, j, this.boatPos)){
                            child.addScore();
                            child.countRemainingPeople();
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }

            }
        }
        return children;
    }


    /*
    print our state
     */
    void print(){
        System.out.println("-------------------------------------");

        for(int i=0; i<cannibalsLeft; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesLeft; i++){
            System.out.print("M");
        }

        System.out.print(" ~~~~~~ ");


        for(int i=0; i<cannibalsRight; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesRight; i++){
            System.out.print("M");
        }
        System.out.print("\n");
        System.out.println("-------------------------------------");
    }

    /*
    our heuristic function which calculates the remaining people which need to be moved from
    the left side of the river to the right
    we add the width of the tree to that score too, so we can use it later in the a star algorithm (f(n) = g(n) + h(n))
     */
    private void countRemainingPeople(){
        int peopleLeft = missionariesLeft + cannibalsLeft;
        if(boatPos == false){
            heuristicScore = 2*peopleLeft;
        }else if(boatPos==true && peopleLeft==1){
            heuristicScore = 1;
        }else if(boatPos==true && peopleLeft>1){
            heuristicScore = 2*peopleLeft - 3;
        }else if(peopleLeft == 0){
            heuristicScore = 0;
        }
        heuristicScore += score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCannibalsLeft() {
        return cannibalsLeft;
    }

    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return missionariesLeft;
    }

    public void setMissionariesLeft(int missionariesLeft) {
        this.missionariesLeft = missionariesLeft;
    }

    public int getCannibalsRight() {
        return cannibalsRight;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public int getMissionariesRight() {
        return missionariesRight;
    }

    public void setMissionariesRight(int missionariesRight) {
        this.missionariesRight = missionariesRight;
    }

    public boolean getBoatPos() {
        return boatPos;
    }

    public void setBoatPos(boolean boatPos) {
        this.boatPos = boatPos;
    }


    public int getBoatCapacity() { return boatCapacity; }

    public void setBoatCapacity(int boatCapacity) { this.boatCapacity = boatCapacity; }

    State getFather()
    {
        return this.father;
    }

    void setFather(State father)
    {
        this.father = father;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this.cannibalsLeft != ((State)obj).cannibalsLeft
                || this.missionariesLeft != ((State) obj).missionariesLeft
                || this.boatPos != ((State) obj).boatPos) {return false;}

        return true;
    }

    @Override
    public int hashCode()
    {
        return 2^cannibalsLeft + 5^ missionariesLeft;
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.heuristicScore, s.heuristicScore); // compare based on the heuristic score.
    }

    public void addScore(){
        score++;
    }
}


