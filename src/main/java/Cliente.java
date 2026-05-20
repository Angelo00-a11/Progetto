public class Cliente extends Utente {

    Ordine ordine;
   // Ordine OrdiniEffettuati[]; C-style
    Ordine[] OrdiniEffettuati;
    Ristorante[] ristorantiDisponibili;

    public Cliente(String nome, String cognome, String email, String ruolo, String via, int civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via,civico,citta,cap);
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        /*#####################*/
        return 0;
    }

    private int Ordina(Ordine ordine){
        //Implementazione specifica

        return 0;
    }
}