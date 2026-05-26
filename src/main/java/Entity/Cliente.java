package Entity;

import java.util.ArrayList;

public class Cliente extends Utente {

    Ordine ordine;
   // Entity.Ordine OrdiniEffettuati[]; C-style
    ArrayList<Ordine> OrdiniEffettuati;
    ArrayList<Ristorante> ristorantiDisponibili;

    public Cliente(String nome, String cognome, String email, String ruolo, String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via,civico,citta,cap);
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        int res;
        if(this.getNome().equals(nome) && this.getCognome().equals(cognome) && this.getEmail().equals(email)){
            res=0;
        }else{
            res=-1;
        }
        return res;
    }

    private int CreaOrdine(){
        //Implementazione specifica
        return 0;
    }

    public int Ordina(Ordine ordine){
        //Implementazione specifica

        ordine.statoOrdine="inviato";
        this.OrdiniEffettuati.add(ordine);
        return 0;
    }
}