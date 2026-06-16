package Controller;

import Entity.GestoreOrdini;
import Entity.Ordine;

import java.util.List;

public class MonitoraOrdineController {

    public static List<Ordine> richiediListaOrdini(Long idCliente){

        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        if (idCliente == null) {
            return null;
        }

        return gestoreOrdini.cercaOrdiniDelCliente(idCliente);
    }

    public static String richiediStatoOrdine(Long idOrdine, Long idCliente) {

        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        if (idOrdine == null || idCliente == null) {
           return null;
       }

       String stato =  gestoreOrdini.cercaStatoOrdine(idOrdine, idCliente);

       if (stato == null ) {
           return "Stato non riconosciuto";
       }

       return stato;
    }

}
