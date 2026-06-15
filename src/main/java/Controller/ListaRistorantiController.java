package Controller;

import Entity.GestoreRistoranti;
import Entity.Ristorante;

import java.util.List;

public class ListaRistorantiController {

    public static List<Ristorante> getRistoranti() {
        GestoreRistoranti gestoreRistoranti = new GestoreRistoranti();
        return gestoreRistoranti.getRistoranti();
    }

}
