package nQueens;

import java.util.Random;

public class Chromosome implements  Comparable<Chromosome>{
    /*
        genes:placement of the queens
        score: heuristic score of the chromosome(how many queens are threatened)
     */
    private int [] genes;
    private int score;

    /*
        N:number of queens
     */
    public Chromosome(int N){
        this.genes = new int[N];
        Random r = new Random();
        /*
            randomize the "genes"
         */
        for(int i=0; i<N;i++){
            this.genes[i] = r.nextInt(N);
        }
        this.score = findScore();
    }

    /*
        "copy" constructor
     */
    public Chromosome(int [] genes,int N){
        this.genes = new int[N];
        for(int i=0; i<N;i++){
            this.genes[i] = genes[i];
        }
        this.score = findScore();
    }

    public int[] getGenes() {
        return genes;
    }

    public int getScore() {
        return score;
    }

    /*
    our "heuristic". calculate in the chromosome how many queens are not in danger
     */
    private int findScore() {
        int not_threats = 0;
        for(int i=0;i<this.genes.length;i++){
            for(int j=i+1; j<this.genes.length;j++){
                if((this.genes[i] != this.genes[j]) && (Math.abs(i-j) != Math.abs(this.genes[i] - this.genes[j]))){
                    not_threats++;
                }
            }
        }
        return not_threats;
    }

    /*
        mutate a gene of the chromosome
     */
    public void mutate(int N){
        Random r = new Random();
        this.genes[r.nextInt(N)] = r.nextInt(N);
        this.score = findScore();
    }

    public void showChromosome(){

        System.out.print("Best Chromosome: ");
        for(int i=0; i<genes.length;i++){
            System.out.print(this.genes[i]);
            System.out.print(" ");
        }
        System.out.print(" with score: ");
        System.out.println(this.score);

        System.out.println("Visual Result:\n");

        for(int i=0; i<genes.length; i++){
            for (int j=0; j<genes.length; j++){
                if (genes[j] == i){
                    System.out.print("|X");
                }else{
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
    }

    @Override
    public boolean equals(Object obj){
        for(int i=0; i<this.genes.length; i++){
            if(this.genes[i] != ((Chromosome)obj).genes[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Chromosome chromosome){
        return this.score - chromosome.score;
    }
}

