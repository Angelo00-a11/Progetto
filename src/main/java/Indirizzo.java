public class Indirizzo {

    private String via;
    private int numeroCivico;
    private String citta;
    private int CAP;

    public Indirizzo(String via, int civico, String citta, int cap) {
        this.via = via;
        this.numeroCivico = civico;
        this.citta = citta;
        this.CAP = cap;
    }

    public String getVia() {
        return via;
    }
    public int getCivico() {
        return numeroCivico;
    }
    public String getCitta() {
        return citta;
    }
    public int getCap() {
        return CAP;
    }

    @Override
    public String toString() {
        return via + " " + numeroCivico + ", " + CAP + ", " + citta;
    }

}
