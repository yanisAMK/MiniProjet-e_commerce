package app;

import java.io.*;
import java.util.ArrayList;

public class WDPInstance implements Cloneable{
    private ArrayList<Offer> offers;
    private int nbOffers;
    private int nbObjects;

    public WDPInstance(String filePath){
        this.offers = new ArrayList<>();
        this.nbObjects = 0;
        this.nbOffers = 0;
        this.readFile(filePath);
    }

    public WDPInstance(WDPInstance instance){
        this.offers = instance.getOffers();
        this.nbObjects = instance.getNbObjects();
        this.nbOffers = instance.getNbOffers();
    }

    private void readFile(String path){
        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        int i = 0;
        try {
            while(!((line = br.readLine()) == null)) { /* read until end of file */
                if (i == 0){
                    String[] information = line.split(" ");
                    this.nbObjects = Integer.parseInt(information[0]);
                    this.nbOffers = Integer.parseInt(information[1]);
                } else{
                    String[] offer = line.split(" ");
                    double bid = Double.parseDouble(offer[0]);
                    ArrayList<Integer> objects = new ArrayList<>();
                    for (int j = 0; j < this.nbObjects; j++) {
                        objects.add(0);
                    }
                    for (int j = 1; j < offer.length; j++) {
                        objects.set(Integer.parseInt(offer[j])-1, 1);
                    }
                    this.offers.add(new Offer(objects, bid, offer.length-1));
                }

                i += 1;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public int getNbOffers() {
        return nbOffers;
    }

    public void setNbOffers(int nbOffers) {
        this.nbOffers = nbOffers;
    }

    public int getNbObjects() {
        return nbObjects;
    }

    public void setNbObjects(int nbObjects) {
        this.nbObjects = nbObjects;
    }

    public void displayData(){
        for (Offer o: this.offers) {
            System.out.println(o.toString());
        }
    }

    public void removeOffer(Offer o){
        this.offers.remove(o);
    }

    public Offer getBestOffer(){
        Offer offer = null;
        double bid = 0;
        for (Offer o:this.offers) {
            if (bid < o.getBid()){
                offer = o;
                bid = o.getBid();
            }
        }
        return offer;
    }

    public WDPInstance clone(){
        return this.clone();
    }
}
