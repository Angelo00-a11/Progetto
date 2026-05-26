package Entity;

public class Amministratore extends Utente {

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



}