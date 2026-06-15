package Entity;

import Database.GestorePersistenza;

import java.util.List;


public class GestoreUtenti {

    private final GestorePersistenza gp;

    public GestoreUtenti() {
        gp = new GestorePersistenza();
    }

    public Utente registraUtente(String nome, String cognome, String email, String ruolo, String via, String civico, int cap, String citta) {
        if (nome == null || cognome == null || email == null || ruolo == null || via == null || civico == null || citta == null) {
            System.err.println("Errore: Impossibile registrare l'utente. Dati mancanti.");
            return null;
        }
         Utente u = null;
        if(ruolo.equalsIgnoreCase("CLIENTE")){
            u = new Cliente(nome, cognome, email, ruolo, via, civico, cap, citta);
        }
        if(ruolo.equalsIgnoreCase("RISTORATORE")){
            u = new Ristoratore(nome,cognome, email, ruolo, via, civico, cap, citta);
        }
        if(ruolo.equalsIgnoreCase("AMMINISTRATORE")){
            u = new Amministratore(nome, cognome, email, ruolo, via, civico, citta, cap);
        }

        boolean esito = gp.salva(u);

        if (esito) {
            gp.aggiorna(u);
            return u;
        } else {
            System.err.println("Errore durante il salvataggio dell'utente nel database.");
            return null;
        }
    }

    public Utente cercaUtentePerId(Long id) {
        return gp.trovaPerId(Utente.class, id);
    }
    public List<Utente> cercaUtentePerEmail(String email) {
        return gp.cercaPerCampo(Utente.class,"email", email);
    }

    public Utente aggiornaUtente( Long idUtente, String email, String via, String civico, int cap, String citta) {

        Utente u = gp.trovaPerId(Utente.class, idUtente);
        if (u == null) {return null;}

        u.setEmail(email);
        Indirizzo i = new Indirizzo(via, civico, cap, citta);
        u.setIndirizzo(i);

        return gp.aggiorna(u);
    }

    //elimina un particolare Utente e tutti gli oggetti contenuti/associati in esso
    public void eliminaUtente(Long idUtente) {
        // non dovrebbe essere necessario rimuovere in modo ricorsivo tutti gli oggetti contenuti/associati nell'utente
        gp.elimina(Utente.class, idUtente);
    }
}
