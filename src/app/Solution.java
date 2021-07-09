package app;

import java.util.ArrayList;

public class Solution {
    private ArrayList<Integer> solution;
    private int nbSelectedOffers;
    private double bid;

    public Solution(WDPInstance instance){
        this.nbSelectedOffers = 0;
        this.bid = 0;
        this.solution = new ArrayList<>();
        for (int i = 0; i < instance.getNbOffers(); i++) {
            this.solution.add(0);
        }
        this.randomSolution(instance);
    }

    public Solution(WDPInstance instance, ArrayList<Integer> solution){
        this.solution = solution;
        this.bid = 0;
        this.nbSelectedOffers = 0;
        for (int i = 0; i < solution.size(); i++) {
            if (solution.get(i) == 1){
                this.nbSelectedOffers += 1;
                this.bid += instance.getOffers().get(i).getBid();
            }
        }
    }

    public Solution(String binary){
        for (int i = 0; i < binary.length(); i++) {
            this.solution.add(Integer.parseInt(String.valueOf(binary.charAt(i))));
        }
        this.nbSelectedOffers = 0;
        for (int value : this.solution){
            if (value == 1)
                this.nbSelectedOffers += 1;
        }
    }

    public boolean isSatisfiable(WDPInstance instance){
        ArrayList<Offer> offers = this.getOffers(instance);
        for (int i = 0; i < offers.size()-1; i++) {
            Offer o1 = offers.get(i);
            for (int j = i+1; j < offers.size(); j++) {
                if (!o1.isCompatible(offers.get(j)))
                    return false;
            }
        }
        return true;
    }


    public String toBinary(){
        String binary = "";
        for (int value : this.solution){
            binary += String.valueOf(value);
        }
        return binary;
    }

    public int toDecimal(){
        return Integer.parseInt(this.toBinary(), 2);
    }

    public ArrayList<Integer> getSolution(){
        return this.solution;
    }

    public int getNbSelectedOffers() {
        return nbSelectedOffers;
    }

    public int getNbSelectedObjects(WDPInstance instance){
        int nb = 0;
        for (int i = 0; i < this.solution.size(); i++) {
            if (this.solution.get(i) == 1)
                nb += instance.getOffers().get(i).getNbObjects();
        }
        return nb;
    }

    public double getBid() {
        return bid;
    }

    public boolean addOfferToSolution(WDPInstance instance, int index){
        ArrayList<Integer> mergedObjects = new ArrayList<>();
        double bid = 0;
        int selectedOffers = 0;
        for (int i = 0; i < this.solution.size(); i++) {
            if (this.solution.get(i) == 1){
                mergedObjects.addAll(instance.getOffers().get(i).getObjects());
                bid += instance.getOffers().get(i).getBid();
                selectedOffers += 1;
            }
        }
        Offer merged = new Offer(mergedObjects, bid, selectedOffers);
        if (this.solution.get(index) == 0){
            if(instance.getOffers().get(index).isCompatible(merged)){
                this.solution.set(index, 1);
                this.nbSelectedOffers += 1;
                return true;
            }
        }
        return false;
    }

    private void randomSolution(WDPInstance instance){
        //Generating random solution with RK
        ArrayList<Double> rk = new ArrayList<Double>();
        for (int i = 0; i < instance.getOffers().size(); i++) {
            rk.add(Math.random());
        }
        int i = 0;
        while (rk.size() > 0) {
            double max = rk.get(0);
            int index = 0;
            for (int j = 0; j < rk.size(); j++) {
                if (max < rk.get(j)){
                    max = rk.get(j);
                    index = j;
                }
            }
            if (i == 0){
                this.solution.set(index, 1);
                this.bid += instance.getOffers().get(index).getBid();
            }else{
                ArrayList<Offer> offers = this.getOffers(instance);
                Offer offerToAdd = instance.getOffers().get(index);
                boolean compatible = true;
                for (Offer o: offers) {
                    if (!offerToAdd.isCompatible(o))
                        compatible = false;
                }
                if (compatible){
                    this.solution.set(index, 1);
                    this.bid += offerToAdd.getBid();
                }
            }
            rk.remove(index);
            i += 1;
        }

    }

    public void updateSolution(WDPInstance instance, int nbOffers){
        ArrayList<Integer> mergedObjects = new ArrayList<>();
        double bid = 0;
        int selectedOffers = 0;
        for (int i = 0; i < this.solution.size(); i++) {
            if (this.solution.get(i) == 1){
                mergedObjects.addAll(instance.getOffers().get(i).getObjects());
                bid += instance.getOffers().get(i).getBid();
                selectedOffers += 1;
            }
        }

        Offer merged = new Offer(mergedObjects, bid, selectedOffers);

        int offersToSelect = nbOffers - this.nbSelectedOffers;
        if (offersToSelect > 0){
            int j = 0;
            for (int i = 0; i < offersToSelect; i++) {
                boolean compatible = false;
                while(j<instance.getNbOffers() && !compatible){
                    if (merged.isCompatible(instance.getOffers().get(j))){
                        compatible = true;
                        mergedObjects = merged.getObjects();
                        mergedObjects.addAll(instance.getOffers().get(j).getObjects());
                        bid = merged.getBid() + instance.getOffers().get(j).getBid();

                        merged = new Offer(mergedObjects, bid, mergedObjects.size());
                        this.solution.set(j, 1);
                        this.nbSelectedOffers += 1;
                    }
                    j += 1;
                }
            }
        }
    }

    public void updateBid(WDPInstance instance) {
        this.bid = 0;
        for (int i = 0; i < this.solution.size(); i++) {
            if (this.solution.get(i)==1){
                this.bid += instance.getOffers().get(i).getBid();
            }
        }
    }

    public ArrayList<Offer> getOffers(WDPInstance instance){
        ArrayList<Offer> offers = new ArrayList<>();
        for (int i = 0; i < this.solution.size(); i++) {
            if (this.solution.get(i) == 1){
                offers.add(instance.getOffers().get(i));
            }
        }
        return offers;
    }
}
