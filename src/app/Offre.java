package app;

import java.util.ArrayList;

public class Offre {
   private String total_price;
   private ArrayList<String> objects;

    public int getNbObjects() {
        return nbObjects;
    }

    public void setNbObjects(int nbObjects) {
        this.nbObjects = nbObjects;
    }

    private int nbObjects;

    public Offre(String total_price) {
        this.total_price = total_price;
        this.objects = new ArrayList<>();
    }
    public void addObjects(ArrayList<String> os){
        this.objects = os;
        this.nbObjects = this.objects.size();
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public ArrayList<String> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<String> objects) {
        this.objects = objects;
    }
}
