package Entity;

import java.util.ArrayList;

public class Ristoratore extends Utente {

    ArrayList<Ristorante> ristorantiGestiti;

    public Ristoratore(String nome, String cognome, String email, String ruolo,String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo,via,civico,citta,cap);
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
    public void addRistorante(Ristorante r){
        ristorantiGestiti.add(r);
    }
    public void removeRistorante(Ristorante r){
        ristorantiGestiti.remove(r);
    }

}