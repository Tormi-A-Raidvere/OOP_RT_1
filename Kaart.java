package com.example.javafx;

public class Kaart {
    private String esikylg;
    private String tagakylg;
    private boolean opitud;
    // Uue kaardi konstruktor
    public Kaart(String esikylg, String tagakylg) {
        this.esikylg = esikylg;
        this.tagakylg = tagakylg;
        this.opitud = false;
    }
    // Konstruktor failist lugemiseks
    public Kaart(String esikylg, String tagakylg, boolean opitud) {
        this.esikylg = esikylg;
        this.tagakylg = tagakylg;
        this.opitud = opitud;
    }

    public String getEsikylg() {
        return esikylg;
    }

    public String getTagakylg() {
        return tagakylg;
    }

    public boolean getOpitud() {
        return opitud;
    }
    public void setOpitud(boolean op) {
        this.opitud = op;
    }

    @Override
    public String toString() {
        return esikylg + " — " + tagakylg;
    }
}
