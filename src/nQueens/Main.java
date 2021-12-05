package nQueens;

public class Main {
    public static void main(String[] args){
        Genetic g = new Genetic();
        /*
            take the parameters of the search as inputs from the user when starting the program
         */
        String populationSize = args[0];
        String mutationProbability = args[1];
        String loops= args[2];
        String numOfQueens = args[3];
        long start = System.currentTimeMillis();  //calculates time
        Chromosome chromosome = g.geneticAlgorithm(Integer.parseInt(populationSize),Double.parseDouble(mutationProbability),Integer.parseInt(loops),Integer.parseInt(numOfQueens)); //runs genetic algorithm
        long end = System.currentTimeMillis();
        chromosome.showChromosome();
        System.out.println("\nTotal time of searching: " + (double)(end - start) / 1000 + " sec."); //prints time
    }
}
