package app;

import java.util.ArrayList;
import java.util.Random;

public class Genetic {
    public ArrayList<Offre> Offres ;
    public int NbOffres;
    public int PopulationSize;
    public int MaxIterrations;

    public ArrayList<Integer> generateSol(int nbOffres){
        int v;
        Random rand = new Random();
        ArrayList<Integer> solution = new ArrayList<>(nbOffres);
        for(int i = 0; i<nbOffres; i++) {
            v = rand.nextInt(2);
            if (v == 1 & checkConflict(i, solution)) {
                solution.set(i, 1);
            } else {
                solution.set(i, 0);
            }
        }
        return solution;
    }

    //cherche les confilits entres les offes s'il y en a
    public boolean checkConflict(int i,ArrayList<Integer> solution){
        ArrayList<String> objecti = Offres.get(i).getObjects();
        ArrayList<String> objectj ;
        for(int j = 0; j< i; j++){
            if(solution.get(j) == 1 ){
                objectj = Offres.get(j).getObjects();
                for(String obj : objecti){
                    if(objectj.contains(obj)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Génere une population
    public  ArrayList<ArrayList<Integer>> generatePop(int popsize){
        ArrayList<ArrayList<Integer>> population = new ArrayList<>();
        for(int i = 0 ; i<popsize; i++){
            population.add(generateSol(this.NbOffres));
        }
        return population ;
    }


        //TODO: ajouter une fonction factice solve avec le calcul du temps d'execution
        // TODO: ajouter max iteration pour l'interface




}
