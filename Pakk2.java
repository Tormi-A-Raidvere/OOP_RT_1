import java.util.ArrayList;
import java.util.List;

public class Pakk {
    private List<Kaart> kaardid;

    public Pakk(List<Kaart> kaardid) {
        this.kaardid = new ArrayList<>();
    }

    public void lisaKaart(Kaart kaart){
        kaardid.add(kaart);
    }
    public Kaart getKaart(int i) {
        return kaardid.get(i);
    }
    public void eemaldaKaart(Kaart kaart){
        kaardid.remove(kaart);
    }


    public int pakiSuurus(){
        return kaardid.size();
    }
    public boolean onTühi(){
        return kaardid.isEmpty();
    }


}
