package Controller;

import Entity.GestoreOrdini;
import Entity.Ordine;

import java.util.List;

public class MonitoraOrdineController {

    private final GestoreOrdini gestoreOrdini;

    public MonitoraOrdineController()
    {
        this.gestoreOrdini = new GestoreOrdini();
    }


    public List<Ordine> richiediListaOrdini(Long idCliente)
    {
        if (idCliente == null)
        {
            return null;
        }

        return gestoreOrdini.cercaOrdiniDelCliente(idCliente);
    }

    public String richiediStatoOrdine(Long idOrdine, Long idCliente)
    {
       if (idOrdine == null || idCliente == null)
       {
           return null;
       }

       String stato =  gestoreOrdini.cercaStatoOrdine(idOrdine, idCliente);

       if (stato == null )
       {
           return "Stato non riconosciuto";
       }

       return stato;
    }

}
