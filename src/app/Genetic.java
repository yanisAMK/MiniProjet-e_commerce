package app;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Genetic {

    public static Solution startSearch(WDPInstance instance, int populationSize, int maxIter, int mutationRate){
        ArrayList<Solution> population = new ArrayList<>();
        ArrayList<Solution> closedPopulation = new ArrayList<>();
        ArrayList<Solution> newGeneration = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(new Solution(instance));
        }

        population.sort(Comparator.comparing(Solution::getBid));
        Collections.reverse(population);

        for (int i = 0; i < maxIter; i++) {
            ArrayList<Solution> children = new ArrayList<>();
            for (int j = 0; j < populationSize/2; j++) {
                //Select 2 parents randomly
                int index1 = (int) ((Math.random() * (population.size() - 0)) + 0);
                Solution parent1 = getBest(population);
                closedPopulation.add(parent1);
                //population.remove(index1);
                int index2 = (int) ((Math.random() * (population.size() - 0)) + 0);
                Solution parent2 = getBest(population);
                closedPopulation.add(parent2);
                //population.remove(index2);

                int kPoint = (int) ((Math.random() * ((instance.getOffers().size()-1) - 1)) + 1);
                children = crossOver(instance, parent1, parent2, kPoint);


                if (mutationRate > 0){
                    for (int k = 0; k < mutationRate; k++) {
                        int mutIndex = (int) ((Math.random() * (instance.getOffers().size() - 0)) + 0);
                        mutate(instance, children.get(0), mutIndex);
                        mutate(instance, children.get(1), mutIndex);
                    }
                }

                if (children.get(0).isSatisfiable(instance))
                    newGeneration.add(children.get(0));
                if (children.get(1).isSatisfiable(instance))
                    newGeneration.add(children.get(1));

            }
            ArrayList<Solution> allSol = new ArrayList<>();
            allSol.addAll(closedPopulation);
            allSol.addAll(newGeneration);
            allSol.sort(Comparator.comparing(Solution::getBid));
            Collections.reverse(allSol);
            population.clear();
            closedPopulation.clear();
            newGeneration.clear();
            for (int j = 0; j < populationSize; j++) {
                population.add(allSol.get(j));
            }
        }

        return population.get(0);

    }

    public static ArrayList<Solution> crossOver(WDPInstance instance, Solution parent1, Solution parent2, int point){
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();

        for (int i = 0; i < point; i++) {
            child1.add(parent1.getSolution().get(i));
            child2.add(parent2.getSolution().get(i));
        }

        for (int i = point; i < parent1.getSolution().size(); i++) {
            child1.add(parent1.getSolution().get(i));
            child2.add(parent2.getSolution().get(i));
        }

        Solution childSol1 = new Solution(instance, child1);
        Solution childSol2 = new Solution(instance, child2);

        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.add(childSol1);
        solutions.add(childSol2);

        return solutions;
    }

    public static void mutate(WDPInstance instance, Solution solution, int index){
        solution.getSolution().set(index, Math.abs(solution.getSolution().get(index) - 1));
        solution.updateBid(instance);
    }

    public static Solution getBest(ArrayList<Solution> population){
        population.sort(Comparator.comparing(Solution::getBid));
        Collections.reverse(population);
        Solution sol = population.get(0);
        population.remove(0);
        return sol;
    }
}
