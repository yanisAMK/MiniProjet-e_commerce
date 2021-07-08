package app;

import java.util.ArrayList;
import java.util.Random;

public class Genetic {

    public void generateSol(int nbOffres){
        int v;
        Random rand = new Random();
        ArrayList<Integer> solution = new ArrayList<>(nbOffres);
        for(int i = 0; i<nbOffres; i++) {
            v = rand.nextInt(2);
            if (v == 1 & checkConflict(i)) {
                solution.set(i, 1);
            } else {
                solution.set(i, 0);
            }
        }
    }
        // creer une solution qui est un vecteure d'offres
        ///vecteur[i] = 1 si l'offre est selectionné
        //            = 0 sinon

        // pour voir si l'offre a et b ont un objet en commun
        //      verifier dans l'arraylist des ofres avec les indexes de a et b

        // TODO: ajouter max iteration pour l'interface




}
