package sample;

import Model.Kategorie;
import Model.Produkt;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import static java.lang.Integer.parseInt;
import static sample.Controller.*;

public class Main extends Application {
    public static Connection polaczenie;
    public static String sql;//  = getPolecenie(); //"Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza
    // left outer join
    // " +
//            "stan on baza" +
//            ".id=idproduktu;";
    public static String skl;//  = getPolecenie();//"Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza
    // left outer join
    // " +
//            "stan on baza.id=idproduktu;";
    public static void main(String[] args) {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            polaczenie = DriverManager.getConnection("jdbc:sqlite:baza.db");
//        }
//        catch (Exception e) {
//            System.out.println("Błąd, błąd");
//        }
        polaczenie=polacz();
        launch(args);
    }
    public static Integer klikniete= 0;
    public static Stage window;
    @FXML
    public static TableView<Produkt> tabela;
    public static TableView<Produkt> braki;
    public static HBox dodawanieProduktu;
    public static HBox edycja;
    public static HBox uzup;
    public static HBox akcja;
    public static TextField poleNazwy;
    public static TextField poleZlotych;
    public static TextField poleMateusz;
    public static TextField polePowinnoMateusz;
    public static TextField poleKsiegarnia;
    public static TextField polePowinnoKsiegarnia;
    public static Button    przyciskDodawania;
    public static TextField poleGroszy;
    public static ComboBox wybierzKat;
    public static Button usun;
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Baza danych sklepu \"Mateusz\"");
        window.setOnCloseRequest(e-> Platform.exit());
        tabela = new TableView();

//          ***********************************************************************
//        *                 COLUMNS ARE HIDDEN BELOW, METJU                      *
//        ***********************************************************************

//        TableColumn<Produkt, Integer> kolId = new TableColumn<>("ID");
//        kolId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        kolId.prefWidthProperty().bind(tabela.widthProperty().multiply(0.03));
//        kolId.

        TableColumn<Produkt, String> kolNazw = new TableColumn<>("Nazwa");
        kolNazw.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        kolNazw.prefWidthProperty().bind(tabela.widthProperty().multiply(0.38));

        TableColumn<Produkt, Double> kolCen = new TableColumn<>("Cena");
        kolCen.setCellValueFactory(new PropertyValueFactory<>("cena"));
        kolCen.prefWidthProperty().bind(tabela.widthProperty().multiply(0.15));

        TableColumn<Produkt, String> kolKategoria = new TableColumn<>("Kategoria");
        kolKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        kolKategoria.prefWidthProperty().bind(tabela.widthProperty().multiply(0.23));


        TableColumn<Produkt, Integer> kolKsiegarnia = new TableColumn("Rynek");
        kolKsiegarnia.setCellValueFactory(new PropertyValueFactory("naRynku"));
        kolKsiegarnia.prefWidthProperty().bind(tabela.widthProperty().multiply(0.07));

        TableColumn<Produkt, Integer> kolNaMiejscu = new TableColumn<>("Dom");
        kolNaMiejscu.setCellValueFactory(new PropertyValueFactory<>("naMiejscu"));
        kolNaMiejscu.prefWidthProperty().bind(tabela.widthProperty().multiply(0.07));

        TableColumn<Produkt, Integer> kolSuma = new TableColumn<>("Razem");
        kolSuma.setCellValueFactory(new PropertyValueFactory<>("wszystkoRazem"));
        kolSuma.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));

        tabela.getColumns().addAll(
                /*kolId,*/ kolNazw, kolCen, kolNaMiejscu, kolKsiegarnia,  kolSuma, kolKategoria);

        wybierzKat = new ComboBox<Kategorie>(getKategorie());
        wybierzKat.getSelectionModel().selectFirst();
        wybierzKat.setCellFactory(new Callback<ListView<Kategorie>, ListCell<Kategorie>>() {
            @Override
            public ListCell<Kategorie> call(ListView<Kategorie> param) {
                return new ListCell<Kategorie>(){
                    public void updateItem(Kategorie item, boolean empty){
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(item.getNazwaKategrie());
                            setGraphic(null);
                        }
                        else{
                            setText(null);
                        }
                    }
                };
            }
        });

        wybierzKat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kategorie>() {
            @Override
            public void changed(ObservableValue<? extends Kategorie> observable, Kategorie oldValue, Kategorie newValue) {
                System.out.println(newValue.getNazwaKategrie());
                String polecenie=getPolecenie(newValue.getNazwaKategrie());
//                if(newValue.getNazwaKategrie().equals("wszystkie"))
//                    {polecenie = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join stan on baza" +
//                        ".id=idproduktu;";}
//                else{
//                    polecenie = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join stan on baza" +
//                            ".id=idproduktu where kategoria=\""+newValue.getNazwaKategrie()+"\";";
//                    }
                try{
                    tabela.setItems(getProdukty(polecenie));
                    }catch(Exception e){System.out.println("nie wybrane chyba\n"+e);}
            }
        });

        sql=getPolecenie(wybierzKat.getValue().toString());
        tabela.setItems( getProdukty(sql));






        poleNazwy = new TextField();
        poleNazwy.setPromptText("Nazwa produktu");

        poleZlotych = new TextField();
        poleZlotych.setPromptText("Zł");
        poleZlotych.prefWidthProperty().setValue(35);//setPrefWidth(35);

        poleGroszy = new TextField();
        poleGroszy.setPromptText("Gr");
        poleGroszy.setPrefWidth(35);

        poleMateusz = new TextField();
        poleMateusz.setPrefWidth(40);
        poleMateusz.setPromptText("M");

        polePowinnoMateusz = new TextField();
        polePowinnoMateusz.setPrefWidth(40);
        polePowinnoMateusz.setPromptText("Mp");

        poleKsiegarnia = new TextField();
        poleKsiegarnia.setPrefWidth(40);
        poleKsiegarnia.setPromptText("K");
//layot
        polePowinnoKsiegarnia = new TextField();
        polePowinnoKsiegarnia.setPrefWidth(40);
        polePowinnoKsiegarnia.setPromptText("Kp");

        przyciskDodawania = new Button("Dodaj produkt");

        przyciskDodawania.setOnAction(event -> przyciskDodaj(wybierzKat.getValue().toString(), sql));

        //                    try {
//                        String nazwa = poleNazwy.getText();
//                        Integer k = parseInt(poleKsiegarnia.getText());
//                        Integer m = parseInt(poleMateusz.getText());
//                        Integer kp = parseInt(polePowinnoKsiegarnia.getText());
//                        Integer mp = parseInt(polePowinnoMateusz.getText());
//                        if (nazwa.length() < 1 | kp < 1 | mp < 1|(wybierzKat.getValue().getNazwaKategrie()).equals("wszystkie"))
//                            System.out.println("bez nazwy lub ...");
//                        else {
//                            try {
//                                Double cena = Double.parseDouble(poleZlotych.getText() +
//                                        "." + poleGroszy.getText());
//                                String sql = "Insert into baza values(null,\"" + nazwa + "\", " + cena + ", \"" +
//                                        wybierzKat.getValue() + "\"" + ");";
//                                System.out.println(sql);
//                                polaczenie.createStatement().executeUpdate(sql);
//                                String sqldwa = "Insert into stan values(null, " + k + ", " + m + "," + kp + "," + mp + ");";
//                                polaczenie.createStatement().executeUpdate(sqldwa);
//                                poleGroszy.clear();
//                                poleNazwy.clear();
//                                poleKategoria.clear();
//                                poleKsiegarnia.clear();
//                                polePowinnoKsiegarnia.clear();
//                                polePowinnoMateusz.clear();
//                                poleZlotych.clear();
//                                poleMateusz.clear();
//                            } catch (Exception e) {
//                                System.out.println("Blad dodawania\n" + e);
//                            }
//
//                            try {
//                                tabela.setItems(getProdukty(sql));
//                            } catch (Exception e) {
//                                System.out.println("blad wyswietlania");
//                            }
//                        }
//                    }
//                catch(Exception e){
//                System.out.println("OJOJOJ\n"+e);
//                    }
//
//        }
//        );

        dodawanieProduktu = new HBox(10);
        dodawanieProduktu.getChildren().addAll(wybierzKat, poleNazwy, poleZlotych, poleGroszy, poleMateusz, polePowinnoMateusz, poleKsiegarnia, polePowinnoKsiegarnia, przyciskDodawania);
        dodawanieProduktu.prefHeightProperty().setValue(20);


        edycja=new HBox(10);
        uzup = new HBox(10);
        akcja = new HBox(10);


        braki  = new TableView();
        braki.setItems(getBraki());
        TableColumn<Produkt, String> brakNazwa = new TableColumn<>("Nazwa");
        brakNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        brakNazwa.prefWidthProperty().bind(braki.widthProperty().multiply(0.70));

        TableColumn<Produkt, Integer> brakKsieg = new TableColumn<>("K");
        brakKsieg.setCellValueFactory(new PropertyValueFactory<>("brakK"));
        brakKsieg.prefWidthProperty().bind(braki.widthProperty().multiply(0.15));

        TableColumn < Produkt, Integer > brakMat = new TableColumn<>("M");
        brakMat.setCellValueFactory(new PropertyValueFactory<>("brakM"));
        brakMat.prefWidthProperty().bind(braki.widthProperty().multiply(0.15));

        braki.getColumns().addAll(brakNazwa, brakMat, brakKsieg);



        HBox tlo = new HBox(10);
        VBox layout = new VBox(10);
        VBox layoutdrugi = new VBox(10);

                    ////***********uzupelnianie stanu**************************
        Button wybrany = new Button("Wybierz produkt");
        wybrany.setDisable(true);

        usun = new Button("Usuń");
        usun.setOnAction(event -> usun());
        TextField dodajKsiegarnia = new TextField("0");
        dodajKsiegarnia.setPromptText("rynek");
        dodajKsiegarnia.setPrefWidth(60);
        dodajKsiegarnia.setTooltip( new Tooltip("Wpisz ilość towaru dodawaną do księgarnii"));

        TextField dodajMateusz = new TextField("0");
        dodajMateusz.setPromptText("sklep");
        dodajMateusz.setPrefWidth(60);
        dodajMateusz.setTooltip( new Tooltip("Wpisz ilość towaru dodawaną do \"Mateusza\""));
        Button uzupelnij = new Button("uzupełnij");
                    //************    AKCJA UZUPEŁNIJ    **********************
        uzupelnij.setOnMouseClicked(event -> {
//            if(tabela.getSelectionModel().getSelectedItem().getNazwa()==null) ;

            if(!wybrany.getText().equals("Wybierz produkt")&&klikniete!=0) {
                Integer id = tabela.getSelectionModel().getSelectedItem().getId();
                try {uzupelnij(id, Integer.parseInt(dodajKsiegarnia.getText()), Integer.parseInt(dodajMateusz.getText()));}
                catch(Exception e){System.out.println("Jooo\n"+e);}
                try{   tabela.setItems(getProdukty(getPolecenie(wybierzKat.getValue().toString())));
                        braki.setItems(getBraki());
//               System.out.println(wybierzKat.getValue().toString());
                }
                catch (Exception e){System.out.println("blad wyswietlania");}
            }
        });
                    ////////***************************************************
        tabela.setOnMouseClicked(event ->
        {
            try {
                wybrany.setText(tabela.getSelectionModel().getSelectedItem().getNazwa());
                klikniete = tabela.getSelectionModel().getSelectedItem().getId();
                System.out.println(tabela.getSelectionModel().getSelectedItem().getNazwa());
                }
            catch (Exception e) {
                System.out.println("trzeba coś wybrać kolego");
            }
        });
                    //**********************************************************

        akcja.getChildren().addAll(wybrany, usun);
        uzup.getChildren().addAll(dodajMateusz, dodajKsiegarnia,  uzupelnij);


        layoutdrugi.prefWidthProperty().bind(window.widthProperty().multiply(0.20));
        layout.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        layout.getChildren().addAll(dodawanieProduktu, tabela);

        layoutdrugi.getChildren().addAll(akcja,uzup, braki);
        layout.setPadding(new Insets(5,5,0,10));
        layoutdrugi.setPadding(new Insets(5,10,10,0));
//        layoutdrugi.prefWidthProperty().bind(window.heightProperty().add(-40));

//        uzup.prefHeightProperty().bind(layoutdrugi.heightProperty().subtract(40));
        uzup.prefHeightProperty().setValue(40);
        wybrany.prefHeightProperty().setValue(30);
        braki.prefHeightProperty().bind(layoutdrugi.heightProperty().subtract(40));

        tabela.prefHeightProperty().bind(layout.heightProperty().subtract(40));


        tlo.getChildren().addAll(layout, layoutdrugi);
        Scene scena = new Scene(tlo, 1200, 550);
        window.setScene(scena);
        primaryStage.show();
    }




//    public ObservableList getKategorie(){
//            ObservableList<Kategorie> kat = FXCollections.observableArrayList();
//            kat.add(new Kategorie("wszystkie"));
//            try{
//            String sql = "Select * from kategorie;";
//            ResultSet wynik = polaczenie.createStatement().executeQuery(sql);
//            while(wynik.next()){
////              /*  kat.add(new Kategorie(wynik.getInt("id"),wynik.getString("kategorie")));*/
//                kat.add(new Kategorie(wynik.getString("nazwa")));
//            }
//        }
//        catch(Exception e){
//            System.out.println("Błąd kolego\n"+e);
//        }
//        return kat;
//    }

//    public ObservableList<Produkt> getProdukty(/*Connection polaczenie*/) throws SQLException {
//        String sql = " Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join stan on baza" +
//                ".id=idproduktu;";
//        ObservableList<Produkt> produkty = FXCollections.observableArrayList();
//        try{
//            ResultSet wynik =polaczenie.createStatement().executeQuery(sql);
//
//            while (wynik.next()) {
//                Integer id = wynik.getInt("id");
//                String nazwa = wynik.getString("nazwa");
//                Double cena = wynik.getDouble("cena");
//                String kategoria = wynik.getString("kategoria");
//                Integer ksiegarnia  = wynik.getInt("ksiegarnia");
//                Integer mateusz = wynik.getInt("mateusz");
//////////////////CHECK   *****************************************************************************************
//                produkty.addAll(new Produkt(id, nazwa, cena, kategoria, ksiegarnia, mateusz));
////                System.out.println("W ksiegarnii: "+ ksiegarnia);
//            }
//        }
//        catch (Exception e) {
//            System.out.println("Błąd, błąd\n"+e);
//        }
//        return produkty;
//    }

//    public ObservableList<Produkt> getBraki(){
//        ObservableList<Produkt> brakuje = FXCollections.observableArrayList();
//        String sql = "Select id, nazwa, kategoria, mateusz, mati, ksiegarnia, rynek from " +
//                "baza inner join stan on idproduktu=id where mati>mateusz or rynek>ksiegarnia;";
//        try{
//            ResultSet wynik = polaczenie.createStatement().executeQuery(sql);
//            while(wynik.next()){
//
//            }
//
//        }
//        catch(Exception e){
//            System.out.println("Braki\n"+e);
//        }
//
//        return brakuje;
//    }



}



