package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OffreLite{
    private StringProperty price;
    private StringProperty size;

    public OffreLite(String price, String size) {
        this.price = new SimpleStringProperty(price);
        this.size = new SimpleStringProperty(size);
    }

    public StringProperty getPrice(){
        return this.price;
    }
    public StringProperty getSize(){
        return this.size;
    }
}

