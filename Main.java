import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Pakk opitavad = new Pakk(new ArrayList<>());
        Pakk teatud = new Pakk(new ArrayList<>()); //Juba arvatud kaartide hoidmiseks

        while (true) {
            JTextField moisteVali = new JTextField();
            JTextField selgitusVali = new JTextField();

            Object[] sisestusPaneel = {
                    "Mõiste (esikülg):", moisteVali,
                    "Selgitus (tagakülg):", selgitusVali
            };

            int valik = JOptionPane.showConfirmDialog(null, sisestusPaneel, "Lisa uus õpikaart", JOptionPane.OK_CANCEL_OPTION);

            if (valik == JOptionPane.OK_OPTION) {
                String moiste = moisteVali.getText().trim();
                String selgitus = selgitusVali.getText().trim();

                if (!moiste.isEmpty() && !selgitus.isEmpty()) {
                    opitavad.lisaKaart(new Kaart(moiste, selgitus));
                } else {
                    JOptionPane.showMessageDialog(null, "Mõlemad väljad peavad olema täidetud!", "Viga", JOptionPane.ERROR_MESSAGE);
                }

                int veel = JOptionPane.showConfirmDialog(null, "Kas soovid veel kaarte lisada?", "Küsimus", JOptionPane.YES_NO_OPTION);
                if (veel == JOptionPane.NO_OPTION) {
                    break;
                }
            } else {
                //Kui vajutatakse cancel või ristist kinni, lõpetame sisestamise
                break;
            }
        }

        Random random = new Random();
        //Tervitus
        JOptionPane.showMessageDialog(null, 
                "Tere tulemast õpikaartide rakendusse!\nProgramm kuvab kaarte seni, kuni tead kõiki mõisteid.", 
                "Info", JOptionPane.INFORMATION_MESSAGE);

        //Mängu peatsükkel - töötab kuni õpitavad kaardid otsa saavad või vajutatakse "Lõpeta"
        while (!opitavad.onTyhi()) {
            
            //Valime suvalise kaardi indeksi
            int suvalineIndeks = random.nextInt(opitavad.pakiSuurus());
            Kaart praeguneKaart = opitavad.getKaart(suvalineIndeks);

            //Kuvame kaardi esikülje
            Object[] valik1 = {"Näita vastust", "Lõpeta"};
            int vastus1 = JOptionPane.showOptionDialog(null,
                    "Mõiste: " + praeguneKaart.getEsikylg(),
                    "Küsimus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    valik1,
                    valik1[0]);

            //Kui kasutaja vajutab risti või valib "Lõpeta"
            if (vastus1 == 1 || vastus1 == JOptionPane.CLOSED_OPTION) {
                break; 
            }

            //Kuvame tagakülje ja küsime, kas teadis
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

            //Loogika vastavalt kasutaja sisendile
            if (vastus2 == 0) { //Kasutaja valis "Tean"
                opitavad.eemaldaKaart(praeguneKaart);
                teatud.lisaKaart(praeguneKaart);
                
                JOptionPane.showMessageDialog(null, 
                        "Tubli! Kaart eemaldatud.\nÕppida on jäänud " + opitavad.pakiSuurus() + " kaarti.", 
                        "Staatus", JOptionPane.PLAIN_MESSAGE);
            } else { //Kasutaja valis "Ei tea"
                JOptionPane.showMessageDialog(null, 
                        "Pole hullu, kaart läheb pakki tagasi ja kordame hiljem uuesti!", 
                        "Staatus", JOptionPane.WARNING_MESSAGE);
            }
        }

        //Programmi lõpetamine
        if (opitavad.onTyhi()) {
            JOptionPane.showMessageDialog(null, "Palju õnne! Sa tead kõiki paki mõisteid!", "Lõpp", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Õppimine pooleli. Jäi veel " + opitavad.pakiSuurus() + " kaarti.", "Lõpp", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
