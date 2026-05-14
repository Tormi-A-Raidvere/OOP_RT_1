package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Main extends Application {

    private PakiHaldur pakiHaldur;
    private Stage peaLava;
    private Pakk aktiivnePakk;
    private Random  random = new Random();

    @Override
    public void start(Stage primaryStage) {
        this.peaLava = primaryStage;
        this.pakiHaldur = new PakiHaldur(); // Laeb olemasolevad pakid failidest

        //Tutvustus
        kuvaInfo("Tere tulemast õpikaartide programmi!\n\n" +
                "Siin saad luua uusi kaardipakke, lisada neisse mõisteid ja neid õppida.\n" +
                "Õppimisel saad mugavalt kasutada klaviatuuri (Tühik, Enter, T, E).");

        näitaPakiValik(); // Käivitame esimese vaate

        primaryStage.setTitle("Õpikaartide Rakendus");
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }

    //Pakkide valik või loomine
    private void näitaPakiValik() {
        VBox juur = new VBox(15);
        juur.setAlignment(Pos.CENTER); // Nõue: Akna suurust muutes püsib sisu keskel
        juur.setPadding(new Insets(20));

        Label pealkiri = new Label("Vali või loo kaardipakk");
        pealkiri.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Olemasolevate pakkide valik
        ComboBox<String> pakiValik = new ComboBox<>();
        pakiValik.getItems().addAll(pakiHaldur.getPakkideNimed());
        pakiValik.setPromptText("Vali olemasolev pakk...");

        Button valiNupp = new Button("Ava valitud pakk");
        valiNupp.setOnAction(e -> {
            try {
                if (pakiValik.getValue() != null) {
                    aktiivnePakk = pakiHaldur.getPakk(pakiValik.getValue());
                    näitaPakiMenüüd();
                } else {
                    kuvaVeateade("Palun vali nimekirjast kaardipakk!");
                }
            } catch (PakkEiEksisteeriErind ex) {
                kuvaVeateade("Viga paki avamisel: " + ex.getMessage());
            }
        });

        // Uue paki loomine
        HBox uuePakiRida = new HBox(10);
        uuePakiRida.setAlignment(Pos.CENTER);
        TextField uuePakiNimi = new TextField();
        uuePakiNimi.setPromptText("Sisesta uue paki nimi");
        Button looNupp = new Button("Loo uus");

        looNupp.setOnAction(e -> {
            String nimi = uuePakiNimi.getText().trim();
            if (nimi.isEmpty()) {
                kuvaVeateade("Paki nimi ei saa olla tühi!"); // Nõue: Ekslike sisestuste reageerimine
            } else if (pakiHaldur.getPakkideNimed().contains(nimi)) {
                kuvaVeateade("Sellise nimega pakk on juba olemas!");
            } else {
                aktiivnePakk = pakiHaldur.looUusPakk(nimi);
                näitaPakiMenüüd();
            }
        });

        // Enter klahv loob paki
        uuePakiNimi.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) looNupp.fire();
        });

        uuePakiRida.getChildren().addAll(uuePakiNimi, looNupp);
        juur.getChildren().addAll(pealkiri, pakiValik, valiNupp, new Label("— VÕI —"), uuePakiRida);

        peaLava.setScene(new Scene(juur, 500, 400));
    }

    // Aktiivse paki peamenüü
    private void näitaPakiMenüüd() {
        VBox juur = new VBox(20);
        juur.setAlignment(Pos.CENTER);

        Label pealkiri = new Label("Aktiivne pakk: " + aktiivnePakk.getNimi());
        pealkiri.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label statLabel = new Label("Õppida on jäänud " + aktiivnePakk.getOpitavad().size() +
                " kaarti (Kokku pakis: " + aktiivnePakk.pakiSuurus() + ")");

        Button opiNupp = new Button("Alusta õppimist");
        opiNupp.setStyle("-fx-font-size: 16px; -fx-base: lightgreen;");
        opiNupp.setOnAction(e -> näitaÕppimist());

        Button lisaNupp = new Button("Lisa uusi kaarte");
        lisaNupp.setOnAction(e -> näitaKaardiLisamist());

        Button tagasiNupp = new Button("Tagasi paki valikusse");
        tagasiNupp.setOnAction(e -> näitaPakiValik());

        juur.getChildren().addAll(pealkiri, statLabel, opiNupp, lisaNupp, tagasiNupp);
        peaLava.setScene(new Scene(juur, 500, 400));
    }

    // Uue kaardi lisamine
    private void näitaKaardiLisamist() {
        GridPane ruudustik = new GridPane();
        ruudustik.setAlignment(Pos.CENTER);
        ruudustik.setHgap(10);
        ruudustik.setVgap(15);

        Label mLabel = new Label("Mõiste (esikülg):");
        TextField moisteVali = new TextField();
        Label sLabel = new Label("Selgitus (tagakülg):");
        TextField selgitusVali = new TextField();

        Button salvestaNupp = new Button("Salvesta kaart (Enter)");
        Button tagasiNupp = new Button("Tagasi menüüsse");

        salvestaNupp.setOnAction(e -> {
            String moiste = moisteVali.getText().trim();
            String selgitus = selgitusVali.getText().trim();

            if (moiste.isEmpty() || selgitus.isEmpty()) {
                kuvaVeateade("Mõlemad väljad peavad olema täidetud!");
            } else {
                aktiivnePakk.lisaKaart(new Kaart(moiste, selgitus)); // Kirjutab otse faili
                kuvaInfo("Kaart edukalt lisatud!");
                moisteVali.clear();
                selgitusVali.clear();
                moisteVali.requestFocus(); // Vii kursor tagasi esimesse lahtrisse
            }
        });

        // Enteriga saab kaardi lisada
        selgitusVali.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) salvestaNupp.fire();
        });

        tagasiNupp.setOnAction(e -> näitaPakiMenüüd());

        ruudustik.add(mLabel, 0, 0);
        ruudustik.add(moisteVali, 1, 0);
        ruudustik.add(sLabel, 0, 1);
        ruudustik.add(selgitusVali, 1, 1);

        HBox nupud = new HBox(10, salvestaNupp, tagasiNupp);
        ruudustik.add(nupud, 1, 2);

        peaLava.setScene(new Scene(ruudustik, 500, 400));
    }

    // Õppimisprotsess
    private void näitaÕppimist() {
        List<Kaart> opitavad = aktiivnePakk.getOpitavad();

        if (opitavad.isEmpty()) {
            VBox juur = new VBox(20);
            juur.setAlignment(Pos.CENTER);
            Label kiri = new Label("Palju õnne!\nKõik selle paki kaardid on õpitud.");
            kiri.setTextAlignment(TextAlignment.CENTER);
            kiri.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Button nulliNupp = new Button("Nulli progress ja alusta uuesti");
            nulliNupp.setOnAction(e -> {
                aktiivnePakk.resetProgress();
                näitaÕppimist();
            });

            Button tagasiNupp = new Button("Tagasi menüüsse");
            tagasiNupp.setOnAction(e -> näitaPakiMenüüd());

            juur.getChildren().addAll(kiri, nulliNupp, tagasiNupp);
            peaLava.setScene(new Scene(juur, 500, 400));
            return;
        }

        // Valime suvalise õpitava kaardi
        Kaart praegune = opitavad.get(random.nextInt(opitavad.size()));

        VBox juur = new VBox(25);
        juur.setAlignment(Pos.CENTER);
        juur.setPadding(new Insets(30));

        Label kysimusLabel = new Label(praegune.getEsikylg());
        kysimusLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        kysimusLabel.setWrapText(true);
        kysimusLabel.setTextAlignment(TextAlignment.CENTER);

        Label vastusLabel = new Label(praegune.getTagakylg());
        vastusLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #555555;");
        vastusLabel.setWrapText(true);
        vastusLabel.setTextAlignment(TextAlignment.CENTER);
        vastusLabel.setVisible(false); // Alguses on vastus peidetud

        // Tühikuga saab ka vastust näha
        Button naitaVastustNupp = new Button("Näita vastust (Tühik)");

        HBox tagasisideNupud = new HBox(20);
        tagasisideNupud.setAlignment(Pos.CENTER);
        Button teanNupp = new Button("Tean (T)");
        teanNupp.setStyle("-fx-base: lightgreen;");
        Button eiTeaNupp = new Button("Ei tea (E)");
        eiTeaNupp.setStyle("-fx-base: lightcoral;");
        tagasisideNupud.getChildren().addAll(teanNupp, eiTeaNupp);
        tagasisideNupud.setVisible(false); // Nähtavaks alles siis, kui vastust on nähtud

        // Sündmused hiirega vajutamisel
        naitaVastustNupp.setOnAction(e -> {
            vastusLabel.setVisible(true);
            naitaVastustNupp.setVisible(false);
            tagasisideNupud.setVisible(true);
        });

        teanNupp.setOnAction(e -> {
            praegune.setOpitud(true);
            // Kuna Pakk klassis on salvestaFaili() private, kasutame andmete faili
            // Eemaldame ja lisame kaardi uuesti,
            // lisaKaart() meetod kutsub faili salvestamise automaatselt välja.
            aktiivnePakk.eemaldaKaart(praegune);
            aktiivnePakk.lisaKaart(praegune);
            näitaÕppimist(); // Järgmine kaart
        });

        eiTeaNupp.setOnAction(e -> {
            näitaÕppimist(); // Võtame lihtsalt uue kaardi, praegune jääb õpitavaks
        });

        Button katkestaNupp = new Button("Katkesta õppimine");
        katkestaNupp.setOnAction(e -> näitaPakiMenüüd());

        // Klaviatuuri sündmused
        juur.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
                if (naitaVastustNupp.isVisible()) naitaVastustNupp.fire();
            } else if (e.getCode() == KeyCode.T && tagasisideNupud.isVisible()) {
                teanNupp.fire();
            } else if (e.getCode() == KeyCode.E && tagasisideNupud.isVisible()) {
                eiTeaNupp.fire();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                katkestaNupp.fire();
            }
        });

        juur.getChildren().addAll(new Label("Mõiste:"), kysimusLabel, naitaVastustNupp,
                vastusLabel, tagasisideNupud, new Label(""), katkestaNupp);

        // Fookus VBoxile, et klahvivajutused kohe registreeruksid
        juur.setFocusTraversable(true);

        peaLava.setScene(new Scene(juur, 500, 400));
        juur.requestFocus();
    }

    // Abimeetodid, tekitavad uued aknad kuhu tekst kuvada
    private void kuvaVeateade(String sisu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Viga");
        alert.setHeaderText(null);
        alert.setContentText(sisu);
        alert.showAndWait();
    }

    private void kuvaInfo(String sisu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(sisu);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}