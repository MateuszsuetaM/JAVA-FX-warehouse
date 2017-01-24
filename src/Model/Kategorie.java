package Model;

/**
 * Created by Mateusz on 17.01.2017.
 */
public class Kategorie {
//    private Integer idKategorie;
    private String nazwaKategrie;


    public Kategorie(/*Integer ajdi,*/ String nazwa){
//        this.idKategorie=ajdi;
        this.nazwaKategrie=nazwa;
    }
    public String getNazwaKategrie() {
        return nazwaKategrie;
    }

    public void setNazwaKategrie(String nazwaKategrie) {
        this.nazwaKategrie = nazwaKategrie;
    }

//    public Integer getIdKategorie() {
//        return idKategorie;
//    }

//    public void setIdKategorie(Integer idKategorie) {
//        this.idKategorie = idKategorie;
//    }
    public String toString(){
        return nazwaKategrie;
    }
}
