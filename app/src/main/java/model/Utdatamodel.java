package model;

/**
 * Created by amit on 30/10/16.
 */

public class Utdatamodel {
    String name;
    String date;
    String prc;

    public Utdatamodel(String name, String date, String prc) {
        this.name = name;
        this.date = date;
        this.prc = prc;
        // this.image=image;
    }
    public String getName(){
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getPrc() {
        return prc;
    }
}
