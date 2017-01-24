package sample;

import Model.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Application;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Kategorie;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import static java.lang.Integer.parseInt;
import static sample.Main.*;

public class Controller {
    public static ObservableList<Produkt> getProdukty(String SQL) throws SQLException {
//        String sqll = " Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join stan on baza" +
//                ".id=idproduktu;";
        SQL=getPolecenie(SQL);
        ObservableList<Produkt> produkty = FXCollections.observableArrayList();
        try{
            ResultSet wynik =polaczenie.createStatement().executeQuery(SQL);

            while (wynik.next()) {
                Integer id = wynik.getInt("id");
                String nazwa = wynik.getString("nazwa");
                Double cena = wynik.getDouble("cena");
                String kategoria = wynik.getString("kategoria");
                Integer ksiegarnia  = wynik.getInt("ksiegarnia");
                Integer mateusz = wynik.getInt("mateusz");
////////////////CHECK   *****************************************************************************************
                produkty.addAll(new Produkt(id, nazwa, cena, kategoria, ksiegarnia, mateusz));
//                System.out.println("W ksiegarnii: "+ ksiegarnia);
            }
        }
        catch (Exception e) {
            System.out.println("Błąd, błąd, blond\n"+e);
        }
        return produkty;
    }

    public static void uzupelnij(Integer ID, Integer rynek, Integer mateusz) throws SQLException {
        String sql = "select mateusz, ksiegarnia from baza where id= "+ID+";";
        Integer mati=0;
        Integer ryn=0;
        ResultSet wynik = polaczenie.createStatement().executeQuery(sql);
        while(wynik.next()){
        mati = wynik.getInt("mateusz");
        ryn = wynik.getInt("ksiegarnia");}
        mati+=mateusz;
        ryn+=rynek;
        System.out.println("M: "+mati+"\n K: "+ryn+"\n id: "+ID);
        sql= "update baza set mateusz="+mati+", ksiegarnia="+ryn+" where id="+ID+";";
        System.out.println(sql);
        polaczenie.createStatement().executeUpdate(sql);
        klikniete=0;
    }

    public static void przyciskDodaj(String tak, String sql){
        try {
            System.out.println(tak);
            String nazwa = poleNazwy.getText();
            Integer k = parseInt(poleKsiegarnia.getText());
            Integer m = parseInt(poleMateusz.getText());
            Integer kp = parseInt(polePowinnoKsiegarnia.getText());
            Integer mp = parseInt(polePowinnoMateusz.getText());
            if (nazwa.length() < 1 | kp < 1 | mp < 1|tak.equals("wszystkie"))
                System.out.println("bez nazwy lub ...");
            else {
                try {
                    Double cena = Double.parseDouble(poleZlotych.getText() +
                            "." + poleGroszy.getText());
//                    sql = "Insert into baza values(null,\"" + nazwa + "\", " + cena + ", \"" +
//                            wybierzKat.getValue() + "\"" + ");";
                    sql = "Insert into baza values(null,\"" + nazwa + "\", " + cena + ", \"" +
                            wybierzKat.getValue() + "\"," +k+","+m+","+kp+","+mp+ ");";
                    System.out.println(sql);
                    polaczenie.createStatement().executeUpdate(sql);
//                    String sqldwa = "Insert into stan values(null, " + k + ", " + m + "," + kp + "," + mp + ");";
//                    polaczenie.createStatement().executeUpdate(sqldwa);
                    poleGroszy.clear();
                    poleNazwy.clear();
                    poleKsiegarnia.clear();
                    polePowinnoKsiegarnia.clear();
                    polePowinnoMateusz.clear();
                    poleZlotych.clear();
                    poleMateusz.clear();
                } catch (Exception e) {
                    System.out.println("Blad dodawania\n" + e);
                }

                try {
                    String skul  = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join " +
                            "stan on baza.id=idproduktu where kategoria=\""+wybierzKat.getValue()+"\";";
                    tabela.setItems(getProdukty(skul));
                    braki.setItems(getBraki());
                } catch (Exception e) {
                    System.out.println("blad wyswietlania");
                }
            }
        }
        catch(Exception e){
            System.out.println("OJOJOJ\n"+e);
        }

    }

    public static ObservableList getKategorie(){
    ObservableList<Kategorie> kat = FXCollections.observableArrayList();
    kat.add(new Kategorie("wszystkie"));
    try{
        String sql = "Select * from kategorie;";
        ResultSet wynik = polaczenie.createStatement().executeQuery(sql);
        while(wynik.next()){
//                kat.add(new Kategorie(wynik.getInt("id"),wynik.getString("kategorie")));
            kat.add(new Kategorie(wynik.getString("nazwa")));
        }
    }
    catch(Exception e){
        System.out.println("Błąd kolego\n"+e);
    }
    return kat;
}

    public static ObservableList<Produkt> getBraki() throws SQLException {
//        String skqle = " Select id, nazwa, ksiegarnia, mateusz, rynek, mati from baza left outer join stan on baza" +
//                ".id=idproduktu where mateusz<mati or ksiegarnia<rynek;";
        String skqle = " Select id, nazwa, ksiegarnia, mateusz, rynek, mati from baza " +
                    " where mateusz<mati or ksiegarnia<rynek;";
        ObservableList<Produkt> braki = FXCollections.observableArrayList();
        try{
            ResultSet wynik =polaczenie.createStatement().executeQuery(skqle);

            while (wynik.next()) {
                Integer id = wynik.getInt("id");
                String nazwa = wynik.getString("nazwa");
                Integer ksiegarnia = wynik.getInt("ksiegarnia");
                Integer mateusz = wynik.getInt("mateusz");
                Integer rynek  = wynik.getInt("rynek");
                Integer mati = wynik.getInt("mati");
////////////////CHECK   *****************************************************************************************
                braki.addAll(new Produkt(id, nazwa, ksiegarnia, mateusz, rynek, mati));
//                System.out.println("W ksiegarnii: "+ ksiegarnia);
            }
        }
        catch (Exception e) {
            System.out.println("Błąd, błąd, blond, brak\n"+e);
        }
        return braki;
    }

    public static String getPolecenie(String walju){
        String polecenie;
        walju=wybierzKat.getValue().toString();
    if(walju.equals("wszystkie"))
        {polecenie = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza;";}
        else{
//            polecenie = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join stan on baza" +
//                    ".id=idproduktu where kategoria=\""+wybierzKat.getValue()+"\";";
        polecenie = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza where kategoria=\""+walju+"\";";
        }
    return polecenie;
}

    public static void usun(){
        try {
            System.out.println(tabela.getSelectionModel().getSelectedItem().getId());
            String sql = "Delete from baza where id=" + tabela.getSelectionModel().getSelectedItem().getId() + ";";
            System.out.println(sql);
            polaczenie.createStatement().executeUpdate(sql);
            }catch(Exception e){
            System.out.println("Błędzicho\n"+e);
            Tooltip news = new Tooltip("Musisz coś wybrać.");
            usun.setTooltip(news);
            news.setAutoHide(false);
        }
        try {
            String skul  = "Select id, nazwa, cena, kategoria, ksiegarnia, mateusz from baza left outer join " +
                    "stan on baza.id=idproduktu where kategoria=\""+wybierzKat.getValue()+"\";";
            tabela.setItems(getProdukty(skul));
            braki.setItems(getBraki());
        } catch (Exception e) {
            System.out.println("blad wyswietlania");
        }

    }

    public static Connection polacz(){
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            polaczenie = DriverManager.getConnection("jdbc:sqlite:baza.db");
//            Statement stmt = polaczenie.createStatement();
        }
        catch (Exception e) {
            /***************************************************
             * DAO - do dokończenia
             ***************************************************/
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                polaczenie = DriverManager.getConnection("jdbc:mysql:baza.db");
            }
            catch (Exception f){
            System.out.println("oho");
            }
            System.out.println("Złe sterowniki bazy\n"+e);
        }
        return polaczenie;
    }
}
