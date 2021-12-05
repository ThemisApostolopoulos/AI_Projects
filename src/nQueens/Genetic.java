package nQueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    /*
    population array: an array with out chromosomes from which chromosomes we will get new ones
    probability array : an array in which our "top" chromosomes index appear more frequently
     */
    private ArrayList<Chromosome> population = null;
    private ArrayList<Integer> probabilityArray = null;

    public Genetic(){}
    /*
        populationSize: the number of chromosomes
        mutationProbability: the probability that a chromosome will mutate. we advise to keep it under 0.1
        loops: the maximum "steps" the algorithm will run.if it exceeds that number, return the best you have found
        N: the number of Queens
     */
    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int loops, int N){
        int solutionScore = findSolutionScore(N);
        //initialize the population array with chromosomes
        this.population = new ArrayList<Chromosome>();
        for(int i=0; i<populationSize; i++){
            this.population.add(new Chromosome(N));
        }
        //initialize the probability array and check the "top" chromosomes
        this.updateProbabilityArray();

        Random r = new Random();
        for(int j=0; j<loops;j++){
            /*
            newPopulation: an array in which we wil store the "offsprings" of the current chromosomes
             */
            ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
            for (int i=0; i<populationSize / 2 ; i++){
                /*
                 get a random number from our probability array.this number is the index of a chromosome
                 so if a chromosome has a high score it has more chances to produce offsprings with other
                 high score chromosomes
                 */
                int parent1Index = this.probabilityArray.get(r.nextInt(this.probabilityArray.size()));
                //thats the first parent
                Chromosome parent1 = this.population.get(parent1Index);
                int parent2Index = this.probabilityArray.get(r.nextInt(this.probabilityArray.size()));
                /*
                now find the second parent, which has to be different than the first parent
                 */
                while(parent2Index == parent1Index){
                    parent2Index = this.probabilityArray.get(r.nextInt(this.probabilityArray.size()));
                }
                //second parent
                Chromosome parent2 = this.population.get(parent2Index);
                //produce their 2 children
                ArrayList<Chromosome> offsprings = this.reproduce(parent1, parent2, N);
                //mutate it if we are under the mutation probability number
                    if(r.nextDouble() < mutationProbability) {

                        offsprings.get(0).mutate(N);
                        offsprings.get(1).mutate(N);
                    }


                /*add them to the new population array
                   and then set the new population array as our population array to continue our search
                 */
              newPopulation.add(offsprings.get(0));
              newPopulation.add(offsprings.get(1));
            }


            this.population = new ArrayList<>(newPopulation);
            /*
                sort based on the heuristic score.
             */
            Collections.sort(this.population,Collections.reverseOrder());

            /*
                algorithm found a solution
             */
            if(this.population.get(0).getScore() == solutionScore){
                System.out.println("Needed " + j + " loops....");
                return this.population.get(0);
            }else{
                this.updateProbabilityArray();
            }
        }
        /*
            could not find solution so return the chromosome with the best score
         */
        System.out.println("Needed " + loops + " loops...");
        return this.population.get(0);
    }

    /*
    find the solution score
     */
    private int findSolutionScore(int N) {
        int sum = 0;
        for(int i=0;i<N;i++){
            sum += i;
        }
        System.out.println("Solution score: "+sum);
        return sum;
    }

    private void updateProbabilityArray() {
        this.probabilityArray = new ArrayList<Integer>();
        /*
         higher score -> more frequent showings of the chromosome in
         the probability array(via its' index)
         */
        for (int i=0; i<this.population.size();i++){
            for(int j = 0; j<this.population.get(i).getScore(); j++){
                probabilityArray.add(i);
            }
        }
    }

    /*
        from 2 chromosomes, produce their children
     */

    private ArrayList<Chromosome> reproduce(Chromosome x, Chromosome y, int N) {
        Random r = new Random();
        /*
            the bound which we will use to produce the child
         */
        int breakIndex = r.nextInt(N-1) + 1;
        int[] childGenes1 =  new int[N];
        int[] childGenes2 = new int[N];
        ArrayList<Chromosome> children = new ArrayList<>();

        /*
        fill the genes of the child up to the bound we calculated with the first parent's genes
         */
        for(int i=0; i<breakIndex;i++){
            childGenes1[i] = x.getGenes()[i];
            childGenes2[i] = y.getGenes()[i];
        }
        /*
        fill the rest with the second parent's genes
         */
        for(int i=breakIndex; i<childGenes1.length;i++){
            childGenes1[i] = y.getGenes()[i];
            childGenes2[i] = x.getGenes()[i];
        }

        children.add(new Chromosome(childGenes1,N));
        children.add(new Chromosome(childGenes2,N));
        return children;

        //return new Chromosome(childGenes,N);
    }

}
