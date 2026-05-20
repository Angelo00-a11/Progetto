public class Ristoratore extends Utente {

    Ristorante[] ristorantiGestiti;

    public Ristoratore(String nome, String cognome, String email, String ruolo,String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo,via,civico,citta,cap);
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        // Implementazione specifica per Ristoratore
        return 0;
    }
    public void addRistorante(){

    }
}