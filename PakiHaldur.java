import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PakiHaldur {
    private List<Pakk> pakid;
    private static final String kaust = "pakid/";

    public PakiHaldur() {
        new File(kaust).mkdirs();
        this.pakid = laadiKõikPakid();
    }

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

    public Pakk getPakk(String nimi) {
        for (Pakk pakk : pakid) {
            if (pakk.getNimi().equals(nimi)) {
                return pakk;
            }
        }
        throw new PakkEiEksisteeriErind(nimi);
    }

    public Pakk luuUusPakk(String nimi) {
        Pakk uusPakk = new Pakk(kaust + nimi + ".csv");
        pakid.add(uusPakk);
        return uusPakk;
    }

    public List<String> getPakkideNimed() {
        List<String> nimed = new ArrayList<>();
        for (Pakk pakk : pakid) {
            nimed.add(pakk.getNimi());
        }
        return nimed;
    }
}