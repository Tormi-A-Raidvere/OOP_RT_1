package com.example.javafx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Klass haldab kaardipakke ja laeb vajalikke faile
public class PakiHaldur {
    private List<Pakk> pakid;
    private static final String kaust = "pakid/";

    public PakiHaldur() {
        // Loome kausta
        new File(kaust).mkdirs();
        this.pakid = laadiKõikPakid();
    }
    //Loeb kaustast kõik Pakid, mis on .csv failide kujul ja loob klassi Pakk objekti
    private List<Pakk> laadiKõikPakid() {
        List<Pakk> pakid = new ArrayList<>();
        File kaustK = new File(kaust);
        File[] failid = kaustK.listFiles((dir, nimi) -> nimi.endsWith(".csv"));

        if (failid != null) {
            for (File fail : failid) {
                pakid.add(new Pakk(fail.getPath()));
            }
        }
        return pakid;
    }
    // Otsib pakki nime järgi, kui ei leia, siis viskab defineeritud erindi
    public Pakk getPakk(String nimi) {
        for (Pakk pakk : pakid) {
            if (pakk.getNimi().equals(nimi)) {
                return pakk;
            }
        }
        throw new PakkEiEksisteeriErind(nimi);
    }
    //Loob uue paki objekti ja lisab selle haldurisse
    public Pakk looUusPakk(String nimi) {
        Pakk uusPakk = new Pakk(kaust + nimi + ".csv");
        pakid.add(uusPakk);
        return uusPakk;
    }
    //Tagastab pakkide nimed
    public List<String> getPakkideNimed() {
        List<String> nimed = new ArrayList<>();
        for (Pakk pakk : pakid) {
            nimed.add(pakk.getNimi());
        }
        return nimed;
    }
}