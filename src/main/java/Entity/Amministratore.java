package Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@DiscriminatorValue("AMMINISTRATORE")
public class Amministratore extends Utente {
    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(
    //    name = "amministratore_ristorante",
    //    joinColumns = @JoinColumn(name = "idAmministratore"),
    //    inverseJoinColumns = @JoinColumn(name = "id_Ristorante")
    //)
    private List<Ristorante> ristorantiConsiderati;

    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(
    //    name = "amministratore_ordine",
    //    joinColumns = @JoinColumn(name = "idAmministratore"),
    //    inverseJoinColumns = @JoinColumn(name = "idOrdine")
    //)
    private List<Ordine> ordiniConsiderati;

    // Costruttore senza parametri (richiesto da JPA)
    public Amministratore() {
        super();
    }

    public Amministratore(String nome, String cognome, String email, String ruolo, String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via, civico, citta, cap);
        this.ristorantiConsiderati = new ArrayList<>();
        this.ordiniConsiderati = new ArrayList<>();
    }

    public List<Ristorante> getRistorantiConsiderati() {
        return ristorantiConsiderati;
    }
    public void setRistorantiConsiderati(List<Ristorante> ristorantiConsiderati) {
        this.ristorantiConsiderati = ristorantiConsiderati;
    }
    public List<Ordine> getOrdiniConsiderati() {
        return ordiniConsiderati;
    }
    public void setOrdiniConsiderati(List<Ordine> ordiniConsiderati) {
        this.ordiniConsiderati = ordiniConsiderati;
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        int res;
        if (this.getNome().equals(nome) && this.getCognome().equals(cognome) && this.getEmail().equals(email)) {
            res = 0;
        } else {
            res = -1;
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