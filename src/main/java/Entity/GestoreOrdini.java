package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Database.GestorePersistenza;

public class GestoreOrdini {

    private final GestorePersistenza gp;

    public GestoreOrdini() {
        //Il GestoreOrdini usa il gp per rendere persistenti gli oggetti del dominio.
        //In questo modo la logica di creazione e collegamento delle Entity resta nel package entity, mentre il codice tecnico di persistenza resta nel package database.
        gp = new GestorePersistenza();
    }


    /* Registra un nuovo ordine nel sistema. */
    public Ordine registraOrdine(Cliente cliente, Indirizzo indirizzoConsegna, List<RigaCarrelloVirtuale> carrello) {
        if (cliente == null || indirizzoConsegna == null || carrello == null || carrello.isEmpty()) {
            System.err.println("Errore: Impossibile registrare l'ordine. Dati mancanti o carrello vuoto.");
            return null;
        }

        Ordine o = cliente.creaOrdine((ArrayList<RigaCarrelloVirtuale>) carrello,indirizzoConsegna);
        cliente.Ordina(o);
        Notifica notifica = o.creaNotifica(o.getStatoOrdine());

        // Collega ogni riga all'ordine
        for (RigaCarrelloVirtuale riga : o.getCarrello()) {
            riga.setOrdine(o);
        }

        // Persisti nella stessa transazione l'ordine e la notifica associata
        boolean esito = gp.salvaTutti(notifica, o);

        if (esito) {
            gp.aggiorna(cliente);
            return o;
        } else {
            System.err.println("Errore durante il salvataggio dell'ordine nel database.");
            return null;
        }
    }


    /* Cerca gli ordini che hanno un certo stato */
    public List<Ordine> cercaOrdiniInStato(String statoOrdine) {
        return gp.cercaPerCampi(Ordine.class, Map.of("statoOrdine", statoOrdine));
    }


    /*
     * Questo metodo carica dal database un Ordine usando la sua Primary Key. Attenzione: questo metodo recupera solo l'oggetto Ordine.
     * Se l'associazione con gli ordini è LAZY, la lista degli ordini potrebbe non essere già caricata quando il metodo restituisce il risultato.
     */
    public Ordine cercaOrdinePerId(Long id) {            //esiste anche la versione full (con tutte le associazioni)... ma non credo mi serva
        return gp.trovaPerId(Ordine.class, id);
    }


    /*
     * Aggiorna i dati di un Ordine esistente.
     * L'Ordine viene prima cercato tramite il suo id e, se esiste, vengono modificati stato e indirizzo di consegna.
     */
    public Ordine aggiornaOrdine(String stato, Indirizzo indirizzoConsegna, Long idOrdine) {

        //Cerco l'ordine nel database usando la sua chiave primaria (Id)
        Ordine ordine = gp.trovaPerId(Ordine.class, idOrdine);
        //Se non esiste, restituisco null
        if (ordine == null) {return null;}

        //Modifico i dati del Ordine
        ordine.setStatoOrdine(stato);
        ordine.setIndirizzoConsegna(indirizzoConsegna);
        //il carrello associato all'ordine deve rimanere invariato
        //se il Cliente desidera modificarlo dovrà eliminare l'ordine corrente e crearne un'altro

        //Aggiorno il Ordine nel database
        return gp.aggiorna(ordine);
    }


    /* Senza una configurazione specifica dell'associazione tra Ordine e Cliente, il database/Hibernate non elimina automaticamente gli oggetti collegati.
     * Se nella classe Ordine fosse stata usata una configurazione corretta con cascade, ad esempio:
     *
     * @OneToMany(mappedBy = "Cliente", cascade = CascadeType.REMOVE)
     * oppure:
     * @OneToMany(mappedBy = "Cliente", cascade = CascadeType.ALL)
     *
     * allora, sarebbe bastato eliminare direttamente il Cliente perchè Hibernate avrebbe eliminato automaticamente anche tutte gli ordini associati.
     */

    //elimina tutti gli ordini associati ad un particolare Cliente
    public void eliminaOrdiniDelCliente(Long idCliente) {

        // Cerco tutti gli ordini associati al cliente
        List<Ordine> ordini = gp.cercaPerCampo(Ordine.class, "cliente.id", idCliente);

        // Elimino gli ordini
        for (Ordine o : ordini) {
            gp.elimina(Ordine.class, o.getId());
        }
        //gp.elimina(Cliente.class, idCliente);    Questo servirebbe eventualmente per eliminare anche il cliente
    }


    public Piatto cercaOCreaPiatto(String nomePiatto) {
        Piatto piatto = gp.cercaPrimoPerCampi(Piatto.class, Map.of("nomePiatto", nomePiatto));
        if (piatto == null) {
            gp.salva(new Piatto(nomePiatto, "Descrizione di " + nomePiatto, 10));
            piatto = gp.cercaPrimoPerCampi(Piatto.class, Map.of("nomePiatto", nomePiatto));
        }
        return piatto;
        // da vedere se è giusto CREARLO
    }

    //METODI PER IL MONITORAGGIO DELL'ORDINE

    public List <Ordine> cercaOrdiniDelCliente(Long idCliente) {
        if (idCliente == null) {
            return null;
        }
        return gp.cercaPerCampo(Ordine.class, "id.Cliente", idCliente); //CERCA TUTTI GLI ORDINI ASSOCIATI AL CLIENTE CON ID "IDCLIENTE" INTERROGANDO IL GP
    }

    //dato un cliente, dato un ordine ne restituisce lo stato
    public String cercaStatoOrdine(Long idCliente, Long idOrdine) {

        Ordine ordine = gp.cercaPrimoPerCampi(Ordine.class, Map.of("id", idOrdine, "cliente.id", idCliente )  );
        if (ordine == null) {
            return null;
        }
        return ordine.getStatoOrdine();
    }
}
