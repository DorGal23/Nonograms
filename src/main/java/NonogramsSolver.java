import java.util.Random;

public class NonogramsSolver {

    private static final int POPULATION_SIZE = 1000;
    private static final int NUMBER_OF_GENERATIONS = 1000;
    private static final int NUMBER_OF_RUNS = 5;
    private static final double MUTATION_CHANCE = 0.05;
    private static final double CROSSOVER_CHANCE = 1.0;

    private static NonogramFactory factory = new NonogramFactory();
    private static Solution[] population = new Solution[POPULATION_SIZE];

    //init 100 random solutions;
    public static void init(){
        Nonogram nonogram = factory.getNonogram();
        for (int i=0; i < population.length; i++){
            population[i] = new Solution(nonogram);
        }
    }

    // Takes two parents, randomly choses a spot and switch their ending from that spot
    public static void crossover(Solution parent1, Solution parent2){
        if (Math.random() < CROSSOVER_CHANCE) {
            int range = parent1.chromosome.length();
            int random = (int)(Math.random()*range);

            String tempEnd1 = parent1.chromosome.substring(random, range);
            String tempEnd2 = parent2.chromosome.substring(random, range);

            //crossover
            parent1.chromosome.replace(random, range, tempEnd2);
            parent2.chromosome.replace(random, range, tempEnd1);


            parent1.calcFitness();
            parent2.calcFitness();
        }
    }

    // Takes two parents, randomly chooses bit from one of them and assigning it to the new offspring
//    public static void crossover(Solution parent1, Solution parent2){
//        if (Math.random() < CROSSOVER_CHANCE) {
//            StringBuilder offspring = new StringBuilder();
//            int range = parent1.chromosome.length();
//
//            for (int i=0; i < range; i++) {
//                if (Math.random() < 0.5) {
//                    offspring.append(parent1.chromosome.charAt(i));
//                } else {
//                    offspring.append(parent2.chromosome.charAt(i));
//                }
//            }
//
//            //crossover
//            parent1.chromosome.replace(0, range, offspring.toString());
//
//            parent1.calcFitness();
//        }
//    }

    // In a very small chance, cause a mutation in the solution @parent
    public static void mutation (Solution parent) {
        if (Math.random() < MUTATION_CHANCE) {
            int range = parent.chromosome.length();
            int random = (int)(Math.random()*range);

            if(parent.chromosome.charAt(random) == '1') {
                parent.chromosome.replace(random,random+1,"0");
            }
            else {
                parent.chromosome.replace(random, random+1, "1");
            }

            parent.calcFitness();
        }
    }

    // Gets 5 random different solutions, and returns the one with the best fitness
    public static Solution selection(){
        Solution [] selected = getNSolutions(10);
        double max = 0;
        int index = 0;

        for (int i=0; i<10; i++){
            if (selected[i].fitness > max){
                max = selected[i].fitness;
                index = i;
            }
        }

        return selected[index];
    }

    //Gets n random different solutions
    private static Solution[] getNSolutions(int n) {
        Solution [] selected = new Solution[n];
        int range = POPULATION_SIZE-1;

        for (int i=0; i<n; i++, range--){
            int random = (int)(Math.random()*range);

            selected[i] = population[random];
            Solution temp = population[range];
            population[range] = selected[i];
            population[random] = temp;
        }

        return selected;
    }

    public static void main(String[] args){

        mainMain();
    }

    public static void mainMain(){
        int generations = NUMBER_OF_GENERATIONS;
        int runs = NUMBER_OF_RUNS;
        Solution best = null;

        for (int i=0; i<runs; i++){
            init();
            double bestFitness = 0;
            Solution bestRunSolution = null;

            for (int j=0; j<generations; j++){
                Solution parent1 = selection();
                Solution parent2 = selection();
                crossover(parent1, parent2);
                mutation(parent1);
                mutation(parent2);
            }

            for (int j=0; j<population.length; j++){
                if (population[j].fitness > bestFitness){
                    bestFitness = population[j].fitness;
                    bestRunSolution = population[j];
                    if (best == null || bestRunSolution.fitness > best.fitness){
                        best = bestRunSolution;
                    }
                }
            }

        }

        System.out.println("Best fitness: " + best.fitness);
        System.out.println(best);
    }
}
//TODO add fitness
//TODO add max fitness breakpoint
//TODO maybe permutation