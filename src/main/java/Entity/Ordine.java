package Entity;

import java.util.ArrayList;

public class Ordine {
    String statoOrdine;

    Notifica notifica;
    Indirizzo indirizzoConsegna;
    Cliente cliente;
    ArrayList<RigaCarrelloVirtuale> carrello;

    public Ordine(String stato, Indirizzo indirizzoConsegna,Cliente cliente){
        this.statoOrdine=stato;
        this.indirizzoConsegna=indirizzoConsegna;
        this.cliente = cliente;
    }

    public void addPiattoAlCarrello(Piatto p,int quantita){

        RigaCarrelloVirtuale rigaCarrello = new RigaCarrelloVirtuale(p,quantita);

        if(carrello==null){
            ArrayList<RigaCarrelloVirtuale> carrello = new ArrayList<RigaCarrelloVirtuale>();
            carrello.add(rigaCarrello);
        }
        else{
            for(int i=0;i<carrello.size();i++){
                if(!rigaCarrello.equals(carrello.get(i))){
                    carrello.add(rigaCarrello);
                }
            }
        }
    }


    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
    public String getStatoOrdine() {
        return statoOrdine;
    }

}
