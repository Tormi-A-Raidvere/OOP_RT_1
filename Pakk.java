import java.util.ArrayList;
import java.util.List;

public class Pakk {
    private List<Kaart> kaardid = new ArrayList<>();

    public void lisaKaart(String esi, String taga) {
        kaardid.add(new Kaart(esi, taga));
    }

    public Kaart getKaart(int i) {
        return kaardid.get(i);
    }
}
