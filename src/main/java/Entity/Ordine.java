package Entity;

import java.util.ArrayList;

public class Ordine {
    String statoOrdine;
    Indirizzo indirizzoConsegna;
    ArrayList<Piatto> carrelloVirtuale;

    public Ordine(String stato, Indirizzo indirizzoConsegna,ArrayList<Piatto> carrelloVirtuale){
        this.statoOrdine=stato;
        this.indirizzoConsegna=indirizzoConsegna;
        this.carrelloVirtuale=carrelloVirtuale;
    }

    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
    public String getStatoOrdine() {
        return statoOrdine;
    }

}
