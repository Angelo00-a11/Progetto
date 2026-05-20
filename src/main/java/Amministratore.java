public class Amministratore extends Utente {

    public Amministratore(String nome, String cognome, String email, String ruolo, String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via,civico,citta,cap);
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        // Implementazione specifica per Amministratore
        return 0;
    }
}