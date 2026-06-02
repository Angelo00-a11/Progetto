package Entity;

import java.util.ArrayList;

public class Amministratore extends Utente {

    ArrayList<Ristorante> ristorantiConsiderati;
    ArrayList<Ristorante> ordiniConsiderati;
    public Amministratore(String nome, String cognome, String email, String ruolo, String via, int civico, String citta, int cap) {
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

    /*public int GetVolOrdini(ArrayList<Ordine> OrdiniConsiderati){
        for(int i=0;i<OrdiniConsiderati.size();i++){
            Ordine o= OrdiniConsiderati.get(i);
            //o.carrelloVirtuale.prezzo;
        }
       // int tot_pagato=
        return tot_pagato/OrdiniConsiderati.size();
    }*/
    /*public int GetTotOrdini(){
        int res=0;
        //
        return res;
    }

    public ArrayList<Ristorante> GetTotOrdini(){
        ArrayList<Ristorante> rist;
        //
        return rist;
    }*/




}