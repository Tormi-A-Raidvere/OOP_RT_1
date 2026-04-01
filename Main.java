import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Pakk> pakid = new ArrayList<>();

    private static int suvalineIndeks(Pakk pakk) {
        int min = 0;
        int max = pakk.pakiSuurus();
        int range = max - min + 1;

        int r = (int)(Math.random() * range) + min;

        return r;
    }

    static void main(String[] args) {
    }
}
