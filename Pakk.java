package com.example.javafx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
// Klass esindab kaardipakki ja salvestab selle faili
public class Pakk {
    private List<Kaart> kaardid;
    private String fail;

    public Pakk(String fail) {
        this.fail = fail;
        this.kaardid = loeFailist(fail);
    }
    // Loeb failist kaardid esikülg;tagakülg;true/false
    private List<Kaart> loeFailist(String fail) {
        List<Kaart> kaardid = new ArrayList<>();
        try (BufferedReader luger = new BufferedReader(new FileReader(fail))) {
            String rida;
            while ((rida = luger.readLine()) != null) {
                String[] osad = rida.split(";");
                if (osad.length == 3) {
                    boolean op = Boolean.parseBoolean(osad[2]);
                    kaardid.add(new Kaart(osad[0], osad[1], op));
                }
            }
        } catch (FileNotFoundException e) {
            // Kui faili ei eksisteeri, tagastatakse tühi list
        } catch (IOException e) {
            System.out.println("Viga faili lugemisel: "+e.getMessage());
        }
        return kaardid;
    }
    // Lisab kaardi ja salvestab selle ka kohe faili
    public void lisaKaart(Kaart kaart) {
        kaardid.add(kaart);
        salvestaFaili();
    }
    // Salvestab pakis olevad kaardid .csv faili
    private void salvestaFaili() {
        try (BufferedWriter kirjutaja = new BufferedWriter(new FileWriter(fail))) {
            for (Kaart kaart : kaardid) {
                kirjutaja.write(kaart.getEsikylg() + ";" + kaart.getTagakylg() + ";" + kaart.getOpitud());
                kirjutaja.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // Abimeetodid
    public Kaart getKaart(int i) {
        return kaardid.get(i);
    }

    public void eemaldaKaart(Kaart kaart) {
        kaardid.remove(kaart);
    }

    public int pakiSuurus() {
        return kaardid.size();
    }

    public boolean onTyhi() {
        return kaardid.isEmpty();
    }
    public String getNimi() {
        return new File(fail).getName().replace(".csv", "");
    }

    public List<Kaart> getOpitavad() {
        List<Kaart> opitavad = new ArrayList<>();
        for (Kaart kaart : kaardid) {
            if (!kaart.getOpitud()) {
                opitavad.add(kaart);
            }
        }
        return opitavad;
    }
    // Paki uuesti õppimiseks muudetakse kõikide kaartide õpitud staatus vääraks
    public void resetProgress() {
        for (Kaart kaart : kaardid) {
            kaart.setOpitud(false);
        }
        salvestaFaili();
    }

    public boolean kõikOnÕpitud() {
        return getOpitavad().isEmpty();
    }
}