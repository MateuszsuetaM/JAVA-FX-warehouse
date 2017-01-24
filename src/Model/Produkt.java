package Model;

/**
 * Created by Mateusz on 16.01.2017.
 */
public class Produkt {
    private Integer id;
    private String nazwa;
    private Double cena;
    private String kategoria;
    private Integer naRynku;
    private Integer naMiejscu;
    private Integer Rynek;
    private Integer Mati;
    private Integer brakK;
    private Integer brakM;

    public Integer getWszystkoRazem() {
        return wszystkoRazem;
    }

    public void setWszystkoRazem(Integer wszystkoRazem) {
        this.wszystkoRazem = wszystkoRazem;
    }

    private Integer wszystkoRazem;
//**********************************BRAKI*******************************************************
        public Produkt(Integer iD, String Nazwa, Integer rynek, Integer mati, Integer rynekPowinno, Integer matiPowinno){
        this.id=iD;
        this.nazwa=Nazwa;
        this.naRynku=rynek;
        this.naMiejscu=mati;
        this.Rynek=rynekPowinno;
        this.Mati=matiPowinno;
        this.brakK=rynekPowinno-rynek;
        this.brakM=matiPowinno-mati;
    }
//    *************************************GET PRODUKTY ********************************************************
    public Produkt(Integer ID, String Nazwa, Double Cena, String Kategoria, Integer wksiegarnii, Integer namiejscu){
        this.id=ID;
        this.nazwa=Nazwa;
        this.cena = Cena;
        this.kategoria=Kategoria;
        this.naRynku =wksiegarnii;
        this.naMiejscu=namiejscu;
        this.wszystkoRazem =wksiegarnii+namiejscu;
        this.brakM=0;
        this.brakK=0;
//        System.out.println("W konstruktorze produktu w Ksiegarnii: "+this.naRynku +"\nrazem:"+ wszystkoRazem);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public int getNaRynku() {
        return naRynku;
    }

    public void setNaRynku(int naRynku) {
        this.naRynku = naRynku;
    }

    public int getNaMiejscu() {
        return naMiejscu;
    }

    public void setNaMiejscu(int naMiejscu) {
        this.naMiejscu = naMiejscu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRynek() {
        return Rynek;
    }

    public void setRynek(Integer rynek) {
        Rynek = rynek;
    }

    public Integer getMati() {
        return Mati;
    }

    public void setMati(Integer mati) {
        Mati = mati;
    }

    public Integer getBrakK() {

        return brakK;
    }

    public void setBrakK(Integer brakK) {
        this.brakK = brakK;
    }

    public Integer getBrakM() {
        return brakM;
    }

    public void setBrakM(Integer brakM) {
        this.brakM = brakM;
    }
}
