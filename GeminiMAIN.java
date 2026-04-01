import javax.swing.JOptionPane;
import java.util.Random;

public class Main {
    
    // Parandatud main meetodi signatuur (puudus "public" ja "static")
    public static void main(String[] args) {
        // 1. Loome kaks pakki
        Pakk opitavad = new Pakk();
        Pakk teatud = new Pakk(); // Kuvatavate/teatud kaartide hoidmiseks (Kuvatud.java asemel)

        // Lisame testandmed (siia võite hiljem failist lugemise lisada)
        opitavad.lisaKaart("Polümorfism", "Mitmekujulisus. Objektorienteeritud programmeerimises oskus käsitleda erinevate klasside objekte ühtse liidese kaudu.");
        opitavad.lisaKaart("Kapseldamine", "Andmete ja neid töötlevate meetodite peitmine klassi sisse.");
        opitavad.lisaKaart("Konstruktor", "Spetsiaalne meetod, mida käivitatakse uue objekti loomisel.");

        Random random = new Random();

        // Tervitus
        JOptionPane.showMessageDialog(null, 
                "Tere tulemast õpikaartide rakendusse!\nProgramm kuvab kaarte seni, kuni tead kõiki mõisteid.", 
                "Info", JOptionPane.INFORMATION_MESSAGE);

        // 2. Mängu peatsükkel - töötab kuni õpitavad kaardid otsa saavad
        while (!opitavad.onTyhi()) {
            
            // Valime suvalise kaardi indeksi
            int suvalineIndeks = random.nextInt(opitavad.pakiSuurus());
            Kaart praeguneKaart = opitavad.getKaart(suvalineIndeks);

            // Kuvame kaardi esikülje
            Object[] valik1 = {"Näita vastust", "Lõpeta"};
            int vastus1 = JOptionPane.showOptionDialog(null,
                    "Mõiste: " + praeguneKaart.getEsikylg(),
                    "Küsimus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    valik1,
                    valik1[0]);

            // Kui kasutaja vajutab risti või valib "Lõpeta"
            if (vastus1 == 1 || vastus1 == JOptionPane.CLOSED_OPTION) {
                break; 
            }

            // Kuvame tagakülje ja küsime, kas teadis
            Object[] valik2 = {"Tean", "Ei tea"};
            int vastus2 = JOptionPane.showOptionDialog(null,
                    "Mõiste: " + praeguneKaart.getEsikylg() + "\nSelgitus: " + praeguneKaart.getTagakylg() + "\n\nKas teadsid seda mõistet?",
                    "Vastus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    valik2,
                    valik2[0]);

            if (vastus2 == JOptionPane.CLOSED_OPTION) {
                break;
            }

            // Loogika vastavalt kasutaja sisendile
            if (vastus2 == 0) { // Kasutaja valis "Tean"
                opitavad.eemaldaKaart(praeguneKaart);
                teatud.lisaKaart(praeguneKaart);
                
                JOptionPane.showMessageDialog(null, 
                        "Tubli! Kaart eemaldatud.\nÕppida on jäänud " + opitavad.pakiSuurus() + " kaarti.", 
                        "Staatus", JOptionPane.PLAIN_MESSAGE);
            } else { // Kasutaja valis "Ei tea"
                JOptionPane.showMessageDialog(null, 
                        "Pole hullu, kaart läheb pakki tagasi ja kordame hiljem uuesti!", 
                        "Staatus", JOptionPane.WARNING_MESSAGE);
            }
        }

        // 3. Programmi lõpetamine
        if (opitavad.onTyhi()) {
            JOptionPane.showMessageDialog(null, "Palju õnne! Sa tead kõiki paki mõisteid!", "Lõpp", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Õppimine pooleli. Jäi veel " + opitavad.pakiSuurus() + " kaarti.", "Lõpp", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
