package Entity;

import java.util.List;
import java.util.Map;

import Database.GestorePersistenza;
import Database.Session;

public class GestoreRistoranti {

    private final GestorePersistenza gp;

    public GestoreRistoranti() {
        gp = new GestorePersistenza();
    }

    public Ristorante registraRistorante(String nome, String descrizione, String via, String civico, String citta,
            int cap, String giorno, int inizioServizio, int fineServizio) {
        if (nome == null || descrizione == null || via == null || civico == null || citta == null) {
            System.err.println("Errore: Impossibile registrare il ristorante. Dati mancanti.");
            return null;
        }
        Ristoratore r = (Ristoratore) Session.getInstance().getUtenteLoggato();
        Ristorante ristorante = r.creaRistorante(nome, descrizione, via, civico, citta, cap, giorno, inizioServizio,
                fineServizio);

        boolean esito = gp.salva(ristorante);

        if (esito) {
            gp.aggiorna(ristorante);
            return ristorante;
        } else {
            System.err.println("Errore durante il salvataggio del ristorante nel database.");
            return null;
        }
    }

    // Logica di ristoranti che si rendono disponibili non esplicita nella traccia
    // li ritorno tutti
    public List<Ristorante> getRistoranti() {
        return gp.cercaPerCampi(Ristorante.class, Map.of());
    }

    public Ristorante cercaRistorantePerId(Long id) {
        return gp.trovaPerId(Ristorante.class, id);
    }

    public Ristorante aggiornaRistorante(Long idRistorante, String nome, String descrizione, String via, String civico,
            String citta, int cap) {

        Ristorante ristorante = gp.trovaPerId(Ristorante.class, idRistorante);
        if (ristorante == null) {
            return null;
        }

        ristorante.setNome(nome);
        ristorante.setDescrizione(descrizione);

        gp.elimina(Indirizzo.class, ristorante.getIndirizzoRistorante().getId());
        Indirizzo i = new Indirizzo(via, civico, cap, citta);
        ristorante.setIndirizzoRistorante(i);

        return gp.aggiorna(ristorante);
    }

    public Ristorante addOrario(Long idRistorante, int inizioServizio, int fineServizio, String giorno) {

        Ristorante r = cercaRistorantePerId(idRistorante);

        for (Data d : r.getOrariApertura()) {
            if (d.getGiorno().equals(giorno) && d.getInizioServizio() == inizioServizio
                    && d.getFineServizio() == fineServizio) {
                d.setInizioServizio(inizioServizio);
                d.setFineServizio(fineServizio);
                d.setGiorno(giorno);
            }
        }
        return gp.aggiorna(r);
    }

    public Ristorante eliminaOrario(Long idRistorante, int inizioServizio, int fineServizio, String giorno) {

        Ristorante r = cercaRistorantePerId(idRistorante);

        for (Data d : r.getOrariApertura()) {
            if (d.getInizioServizio() == inizioServizio && d.getFineServizio() == fineServizio
                    && d.getGiorno().equals(giorno)) {
                gp.elimina(Data.class, d.getId());
            }
        }
        return gp.aggiorna(r);
    }

    public Ristorante eliminaTuttiOrari(Long idRistorante, int inizioServizio, int fineServizio, String giorno) {

        Ristorante r = cercaRistorantePerId(idRistorante);

        for (Data d : r.getOrariApertura()) {
            gp.elimina(Data.class, d.getId());
        }
        return gp.aggiorna(r);
    }
}