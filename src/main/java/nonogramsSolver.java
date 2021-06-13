import java.util.Random;

public class nonogramsSolver {

    private static int rows;
    private static int cols;
    private static int[][] rowsConstraint;
    private static int[][] colsConstraint;

    private static Solution[] population = new Solution[100];

    public static void init(){
        for (int i=0; i < population.length; i++){
            population[i] = new Solution(rows, cols);
        }
    }

    public static void crossover(Solution parent1, Solution parent2){
        if (Math.random() < 0.7) {
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

    public static void mutation (Solution parent) {
        if (Math.random() < 0.04) {
            int range = parent.chromosome.length();
            int random = (int)(Math.random()*range);
            if(parent.chromosome.charAt(random) == '1') {
                parent.chromosome.replace(random,random,"0");
            }
            else {
                parent.chromosome.replace(random, random, "1");
            }
            parent.calcFitness();
        }
    }

    public static Solution selection(){
        Solution [] selected = getNSolutions(5);
        int max = 0;
        int index = 0;

        for (int i=0; i<5; i++){
            if (selected[i].fitness > max){
                max = selected[i].fitness;
                index = i;
            }
        }

        return selected[index];
    }

    private static Solution[] getNSolutions(int n) {
        Solution [] selected = new Solution[n];
        int range = 99;

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
        int generations = 500;
        int runs = 5;
        Solution best = null;

        for (int i=0; i<runs; i++){
            init();
            int bestFitness = 0;
            Solution bestRunSolution;

            for (int j=0; j<generations; j++){
                Solution parent1 = selection();
                Solution parent2 = selection();
                crossover(parent1, parent2);
                mutation(parent1);
                mutation(parent2);
            }

            for (int j=0; i<population.length; i++){
                if (population[i].fitness > bestFitness){
                    bestFitness = population[i].fitness;
                    bestRunSolution = population[i];
                    if (best == null || bestRunSolution.fitness > best.fitness){
                        best = bestRunSolution;
                    }
                }
            }
        }

    }


}
//TODO add fitness
//TODO add max fitness breakpoint
//TODO maybe permutation