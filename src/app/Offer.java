package app;

import java.util.ArrayList;

public class Offer {
    private ArrayList<Integer> objects;
    private double bid;
    private int nbObjects;

    public Offer(ArrayList<Integer> objects, double bid, int nbObjects){
        this.nbObjects = nbObjects;
        this.objects = objects;
        this.bid = bid;
    }

    public boolean isCompatible(Offer offer){
        for (int i = 0; i < this.objects.size(); i++) {
            if (this.objects.get(i) == offer.getObjects().get(i) && this.objects.get(i) == 1)
                return false;
        }
        return true;
    }

    public ArrayList<Integer> getObjects() {
        return objects;
    }

    public double getBid() {
        return bid;
    }

    public int getNbObjects() {
        return nbObjects;
    }

    public void setObjects(ArrayList<Integer> objects) {
        this.objects = objects;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public void setNbObjects(int nbObjects) {
        this.nbObjects = nbObjects;
    }

    public String toString(){
        return "Offre: Enchére: " + this.bid + "||      Nombre d'objets : " + this.nbObjects ;
    }
}
