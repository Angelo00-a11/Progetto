


public class Ordine {
    String statoOrdine;
    Indirizzo indirizzoConsegna;

    public Ordine(String stato,Indirizzo indirizzoConsegna){
        this.statoOrdine=stato;
        this.indirizzoConsegna=indirizzoConsegna;
    }

    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
    public String getStatoOrdine() {
        return statoOrdine;
    }

}
