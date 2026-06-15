package Controller;

import Entity.Amministratore;
import Entity.GestoreRistoranti;
import Entity.Ordine;
import Entity.Ristorante;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AmministratoreController {

    public static Map<String, Object> monitoraSistema(String dataInizioStr, String dataFineStr) {
        // 1. Normalizza e converte
        LocalDate dataInizio = parseData(dataInizioStr);
        LocalDate dataFine = parseData(dataFineStr);

        // 2. Valida l'intervallo
        if (dataInizio.isAfter(dataFine)) {
            throw new IllegalArgumentException("Intervallo date non valido");
        }

        // 3. Ottiene la lista di tutti i Ristoranti dal gestore
        GestoreRistoranti gestore = new GestoreRistoranti();
        List<Ristorante> tuttiRistoranti = gestore.getRistoranti();

        // 4. Filtra i Ristoranti da passare all'entity
        List<Ristorante> ristorantiFiltrati = filtraPerData(tuttiRistoranti, dataInizio, dataFine);

        // 5. Chiama l'Entity
        Amministratore admin = new Amministratore();
        return admin.monitoraSistema(ristorantiFiltrati, dataInizio, dataFine);
    }

    private static LocalDate parseData(String input) {
        // rimuove tutto ciò che non è una cifra: slash, trattini, spazi
        String normalizzata = input.replaceAll("[^0-9]", "");
        if (normalizzata.length() != 8) {
            throw new IllegalArgumentException(
                    "Formato data non valido: usare ggmmaaaa o gg/mm/aaaa o gg-mm-aaaa"
            );
        }
        try {
            return LocalDate.parse(normalizzata, DateTimeFormatter.ofPattern("ddMMyyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data non valida: " + input);
        }
    }

    private static List<Ristorante> filtraPerData(List<Ristorante> daFiltrare, LocalDate dataInizio, LocalDate dataFine) {
        List<Ristorante> ristorantiFiltrati = new ArrayList<>();
        for (Ristorante r : daFiltrare) {
            boolean haOrdiniValidi = false;
            for (Ordine o : r.getOrdiniRicevuti()) {
                LocalDate dataOrdine = o.getData();
                boolean nelPeriodo = !dataOrdine.isBefore(dataInizio) && !dataOrdine.isAfter(dataFine);
                boolean evaso = "evaso".equalsIgnoreCase(o.getStatoOrdine());

                if (nelPeriodo && evaso) {
                    haOrdiniValidi = true;
                    break;
                }
            }
            if (haOrdiniValidi) {
                ristorantiFiltrati.add(r);
            }
        }

        return ristorantiFiltrati;
    }
}
