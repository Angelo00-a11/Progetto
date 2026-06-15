package Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Entity.*;
import java.time.LocalDate;
import java.util.List;

public class JpaUtil {

    /*
     * Istanza unica di JpaUtil.
     *
     * Questa variabile realizza il cuore del pattern Singleton:
     * la classe mantiene internamente l'unica istanza disponibile.
     */
    private static JpaUtil instance;

    /*
     * EntityManagerFactory condivisa.
     *
     * La factory viene creata una sola volta, perché è un oggetto
     * costoso da inizializzare: legge la persistence unit dal file
     * persistence.xml e prepara Hibernate per comunicare con il database.
     */
    private EntityManagerFactory emf;

    /*
     * Costruttore privato.
     *
     * Questo impedisce al resto dell'applicazione di creare oggetti
     * JpaUtil usando new JpaUtil().
     *
     * L'unico modo per ottenere JpaUtil sarà passare dal metodo
     * statico getInstance().
     */
    private JpaUtil() {
        /*
         * Creiamo la EntityManagerFactory usando la persistence unit
         * definita nel file persistence.xml.
         *
         * Il nome "boatyardPU" deve coincidere con:
         *
         * <persistence-unit name="boatyardPU">
         */
        emf = Persistence.createEntityManagerFactory("fooddeliveryPU");
    }

    /*
     * Punto di accesso globale all'unica istanza di JpaUtil.
     *
     * Se l'istanza non esiste ancora, viene creata.
     * Se è già stata creata, viene semplicemente restituita.
     *
     * Questo metodo completa l'applicazione del pattern Singleton.
     */
    public static JpaUtil getInstance() {
        if (instance == null) {
            instance = new JpaUtil();
        }

        return instance;
    }

    /*
     * Crea un nuovo EntityManager.
     *
     * Attenzione: l'EntityManager non è Singleton.
     * Ogni operazione di persistenza deve usare un proprio EntityManager,
     * perché l'EntityManager mantiene lo stato della singola sessione
     * di lavoro con il database.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*
     * Chiude la EntityManagerFactory.
     *
     * Questo metodo va chiamato alla fine dell'applicazione,
     * quando non sono più necessarie operazioni di persistenza.
     */
    public void chiudi() {
        emf.close();
    }

    /*
     * NON FA PARTE DEL DOMINIO - UTILITÀ
     */

    /**
     * Inizializza il database con dati di prova se non ci sono utenti registrati.
     */
    public void inizializzaDatiSeVuoto() {
        EntityManager em = getEntityManager();
        try {
            List<String> emailUtentiTest = List.of(
                    "mario.rossi@email.com",
                    "luca.bianchi@email.com",
                    "giovanni.verdi@email.com",
                    "marco.neri@email.com",
                    "admin@email.com");
            List<String> nomiRistorantiTest = List.of(
                    "Pizzeria Bella Napoli",
                    "Sushi Zen",
                    "Trattoria da Nonna Rosa");

            // Verifica se tutti gli utenti di prova sono gia' presenti
            Long conteggioTest = em.createQuery(
                            "SELECT COUNT(u) FROM Utente u WHERE u.email IN :emailUtentiTest",
                            Long.class)
                    .setParameter("emailUtentiTest", emailUtentiTest)
                    .getSingleResult();
            Long conteggioRistorantiTest = em.createQuery(
                            "SELECT COUNT(r) FROM Ristorante r WHERE r.nome IN :nomiRistorantiTest",
                            Long.class)
                    .setParameter("nomiRistorantiTest", nomiRistorantiTest)
                    .getSingleResult();
            if (conteggioTest == emailUtentiTest.size() && conteggioRistorantiTest == nomiRistorantiTest.size()) {
                System.out.println("Dati di prova gia' presenti nel database. Salto il seeding.");
                return;
            }

            Long totaleUtenti = em.createQuery("SELECT COUNT(u) FROM Utente u", Long.class).getSingleResult();
            if (totaleUtenti > 0) {
                List<String> emailGiaPresenti = em.createQuery(
                                "SELECT u.email FROM Utente u WHERE u.email IN :emailUtentiTest",
                                String.class)
                        .setParameter("emailUtentiTest", emailUtentiTest)
                        .getResultList();

                System.out.println("Database gia' popolato. Aggiungo solo i dati di prova mancanti...");
                em.getTransaction().begin();

                if (!emailGiaPresenti.contains("mario.rossi@email.com")) {
                    em.persist(new Cliente("Mario", "Rossi", "mario.rossi@email.com", "CLIENTE", "Via Roma", "12",
                            100, "Roma"));
                }
                if (!emailGiaPresenti.contains("luca.bianchi@email.com")) {
                    em.persist(new Cliente("Luca", "Bianchi", "luca.bianchi@email.com", "CLIENTE", "Corso Italia",
                            "45", 20100, "Milano"));
                }
                if (!emailGiaPresenti.contains("giovanni.verdi@email.com")) {
                    em.persist(new Ristoratore("Giovanni", "Verdi", "giovanni.verdi@email.com", "RISTORATORE",
                            "Via Garibaldi", "1", 16100, "Genova"));
                }
                if (!emailGiaPresenti.contains("marco.neri@email.com")) {
                    em.persist(new Ristoratore("Marco", "Neri", "marco.neri@email.com", "RISTORATORE", "Via Dante",
                            "2", 90100, "Palermo"));
                }
                if (!emailGiaPresenti.contains("admin@email.com")) {
                    em.persist(new Amministratore("Admin", "Admin", "admin@email.com", "AMMINISTRATORE",
                            "Via Mazzini", "3", "Bologna", 40100));
                }

                em.flush();

                Ristoratore ristoratore1 = em.createQuery(
                                "SELECT r FROM Ristoratore r WHERE r.email = :email",
                                Ristoratore.class)
                        .setParameter("email", "giovanni.verdi@email.com")
                        .getSingleResult();
                Ristoratore ristoratore2 = em.createQuery(
                                "SELECT r FROM Ristoratore r WHERE r.email = :email",
                                Ristoratore.class)
                        .setParameter("email", "marco.neri@email.com")
                        .getSingleResult();

                aggiungiRistoranteDemoSeMancante(em,
                        "Pizzeria Bella Napoli",
                        "La vera pizza napoletana a lievitazione naturale",
                        "Via Vesuvio", "24", "Napoli", 80100,
                        ristoratore1,
                        new Piatto("Margherita", "Pomodoro, mozzarella, basilico fresco, olio EVO", 6.50f),
                        new Piatto("Diavola", "Pomodoro, mozzarella, salame piccante, basilico", 7.50f),
                        new Piatto("Capricciosa", "Pomodoro, mozzarella, prosciutto cotto, funghi, carciofini, olive", 8.50f),
                        new Piatto("Bufalina", "Pomodoro, mozzarella di bufala campana DOP, basilico, olio EVO", 9.00f));

                aggiungiRistoranteDemoSeMancante(em,
                        "Sushi Zen",
                        "Cucina tradizionale giapponese, sushi e sashimi fresco",
                        "Via dei Mille", "10", "Firenze", 50100,
                        ristoratore1,
                        new Piatto("Box Sushi Misto", "12 pezzi assortiti: 4 nigiri, 4 hosomaki, 4 uramaki", 16.00f),
                        new Piatto("Salmone Sashimi", "9 fette di salmone fresco servite con zenzero e wasabi", 13.00f),
                        new Piatto("Tempura Uramaki", "Rotolo con gambero in tempura, avocado, maionese e salsa teriyaki", 11.00f));

                aggiungiRistoranteDemoSeMancante(em,
                        "Trattoria da Nonna Rosa",
                        "Piatti tipici della tradizione romana fatti in casa",
                        "Via del Corso", "102", "Roma", 186,
                        ristoratore2,
                        new Piatto("Carbonara", "Spaghetti con guanciale croccante, tuorlo d'uovo, pecorino romano e pepe nero", 12.00f),
                        new Piatto("Cacio e Pepe", "Tonnarelli freschi con pecorino romano DOP e pepe nero macinato", 10.00f),
                        new Piatto("Amatriciana", "Rigatoni con pomodoro, guanciale e pecorino romano", 11.50f),
                        new Piatto("Tiramisu della Casa", "Dolce al cucchiaio con mascarpone, savoiardi e caffe", 5.50f));

                em.getTransaction().commit();
                System.out.println("Dati di prova mancanti aggiunti con successo.");
                return;
            }

            System.out.println("Utenti di prova assenti o incompleti. Svuoto e avvio il seeding dei dati...");
            em.getTransaction().begin();

            // Svuota le tabelle per evitare conflitti di chiavi uniche (es. email o nomi piatti duplicati)
            em.createQuery("DELETE FROM RigaCarrelloVirtuale").executeUpdate();
            em.createQuery("DELETE FROM Ordine").executeUpdate();
            em.createQuery("DELETE FROM Notifica").executeUpdate();
            em.createQuery("DELETE FROM Piatto").executeUpdate();
            em.createQuery("DELETE FROM Data").executeUpdate();
            em.createQuery("DELETE FROM Ristorante").executeUpdate();
            em.createQuery("DELETE FROM Utente").executeUpdate();
            em.createQuery("DELETE FROM Indirizzo").executeUpdate();

            // 1. Creazione Indirizzi per Utenti (avviene in automatico tramite i
            // costruttori degli Utenti)
            // 2. Creazione Clienti
            Cliente cliente1 = new Cliente("Mario", "Rossi", "mario.rossi@email.com", "CLIENTE", "Via Roma", "12", 100,
                    "Roma");
            Cliente cliente2 = new Cliente("Luca", "Bianchi", "luca.bianchi@email.com", "CLIENTE", "Corso Italia", "45",
                    20100, "Milano");

            // 3. Creazione Ristoratori
            Ristoratore ristoratore1 = new Ristoratore("Giovanni", "Verdi", "giovanni.verdi@email.com", "RISTORATORE",
                    "Via Garibaldi", "1", 16100, "Genova");
            Ristoratore ristoratore2 = new Ristoratore("Marco", "Neri", "marco.neri@email.com", "RISTORATORE",
                    "Via Dante", "2", 90100, "Palermo");

            // 4. Creazione Amministratore (nota: i parametri cap e citta sono invertiti nel
            // costruttore di Amministratore)
            Amministratore admin = new Amministratore("Admin", "Admin", "admin@email.com", "AMMINISTRATORE",
                    "Via Mazzini", "3", "Bologna", 40100);

            // Persistenza Utenti
            em.persist(cliente1);
            em.persist(cliente2);
            em.persist(ristoratore1);
            em.persist(ristoratore2);
            em.persist(admin);

            // 5. Creazione Ristoranti, Orari di Apertura (Data) e Menù (Piatto)
            // Ristorante 1 - Pizzeria
            Ristorante r1 = new Ristorante("Pizzeria Bella Napoli", "La vera pizza napoletana a lievitazione naturale",
                    "Via Vesuvio", "24", "Napoli", 80100, "Lunedì", 12, 23, ristoratore1);
            r1.addOrariApertura(new Data("Lunedì", 12, 15));
            r1.addOrariApertura(new Data("Lunedì", 19, 23));
            r1.addOrariApertura(new Data("Martedì", 12, 15));
            r1.addOrariApertura(new Data("Martedì", 19, 23));
            r1.addOrariApertura(new Data("Sabato", 19, 23));
            r1.addOrariApertura(new Data("Domenica", 19, 23));

            Piatto p1_1 = new Piatto("Margherita", "Pomodoro, mozzarella, basilico fresco, olio EVO", 6.50f);
            Piatto p1_2 = new Piatto("Diavola", "Pomodoro, mozzarella, salame piccante, basilico", 7.50f);
            Piatto p1_3 = new Piatto("Capricciosa", "Pomodoro, mozzarella, prosciutto cotto, funghi, carciofini, olive",
                    8.50f);
            Piatto p1_4 = new Piatto("Bufalina", "Pomodoro, mozzarella di bufala campana DOP, basilico, olio EVO",
                    9.00f);

            r1.getMenu().add(p1_1);
            r1.getMenu().add(p1_2);
            r1.getMenu().add(p1_3);
            r1.getMenu().add(p1_4);

            ristoratore1.addRistorante(r1);
            em.persist(r1);

            // Ristorante 2 - Sushi
            Ristorante r2 = new Ristorante("Sushi Zen", "Cucina tradizionale giapponese, sushi e sashimi fresco",
                    "Via dei Mille", "10", "Firenze", 50100, "Mercoledì", 12, 23, ristoratore1);
            r2.addOrariApertura(new Data("Mercoledì", 19, 23));
            r2.addOrariApertura(new Data("Giovedì", 19, 23));
            r2.addOrariApertura(new Data("Venerdì", 12, 15));
            r2.addOrariApertura(new Data("Venerdì", 19, 23));
            r2.addOrariApertura(new Data("Sabato", 19, 23));

            Piatto p2_1 = new Piatto("Box Sushi Misto", "12 pezzi assortiti: 4 nigiri, 4 hosomaki, 4 uramaki", 16.00f);
            Piatto p2_2 = new Piatto("Salmone Sashimi", "9 fette di salmone fresco servite con zenzero e wasabi",
                    13.00f);
            Piatto p2_3 = new Piatto("Tempura Uramaki",
                    "Rotolo con gambero in tempura, avocado, maionese e salsa teriyaki", 11.00f);

            r2.getMenu().add(p2_1);
            r2.getMenu().add(p2_2);
            r2.getMenu().add(p2_3);

            ristoratore1.addRistorante(r2);
            em.persist(r2);

            // Ristorante 3 - Trattoria
            Ristorante r3 = new Ristorante("Trattoria da Nonna Rosa",
                    "Piatti tipici della tradizione romana fatti in casa", "Via del Corso", "102", "Roma", 186,
                    "Giovedì", 12, 23, ristoratore2);
            r3.addOrariApertura(new Data("Giovedì", 12, 15));
            r3.addOrariApertura(new Data("Giovedì", 19, 23));
            r3.addOrariApertura(new Data("Venerdì", 12, 15));
            r3.addOrariApertura(new Data("Venerdì", 19, 23));
            r3.addOrariApertura(new Data("Sabato", 12, 15));
            r3.addOrariApertura(new Data("Sabato", 19, 23));
            r3.addOrariApertura(new Data("Domenica", 12, 16));

            Piatto p3_1 = new Piatto("Carbonara",
                    "Spaghetti con guanciale croccante, tuorlo d'uovo, pecorino romano e pepe nero", 12.00f);
            Piatto p3_2 = new Piatto("Cacio e Pepe", "Tonnarelli freschi con pecorino romano DOP e pepe nero macinato",
                    10.00f);
            Piatto p3_3 = new Piatto("Amatriciana", "Rigatoni con pomodoro, guanciale e pecorino romano", 11.50f);
            Piatto p3_4 = new Piatto("Tiramisù della Casa", "Dolce al cucchiaio con mascarpone, savoiardi e caffè",
                    5.50f);

            r3.getMenu().add(p3_1);
            r3.getMenu().add(p3_2);
            r3.getMenu().add(p3_3);
            r3.getMenu().add(p3_4);

            ristoratore2.addRistorante(r3);
            em.persist(r3);

            // Forza il flush per generare le chiavi primarie (necessarie per associare le
            // righe degli ordini)
            em.flush();

            // 6. Creazione Storico Ordini (con piatti reali e prezzi calcolati)
            // Ordine 1: Cliente 1 da Pizzeria Bella Napoli, Evaso 5 giorni fa
            Ordine o1 = new Ordine("evaso", cliente1.getIndirizzo(), cliente1);
            o1.setData(LocalDate.now().minusDays(5));
            o1.addPiattoAlCarrello(p1_1, 2); // 2 Margherita
            o1.addPiattoAlCarrello(p1_2, 1); // 1 Diavola
            for (RigaCarrelloVirtuale riga : o1.getCarrello()) {
                riga.setOrdine(o1);
            }
            cliente1.addOrdineEffettuato(o1);
            r1.addOrdine_Ricevuti(o1);

            Notifica n1 = new Notifica("Il tuo ordine n.1 è stato consegnato con successo!", "LETTA");
            o1.setNotifica(n1);
            em.persist(o1);

            // Ordine 2: Cliente 1 da Trattoria Nonna Rosa, Evaso 2 giorni fa
            Ordine o2 = new Ordine("evaso", cliente1.getIndirizzo(), cliente1);
            o2.setData(LocalDate.now().minusDays(2));
            o2.addPiattoAlCarrello(p3_1, 1); // 1 Carbonara
            o2.addPiattoAlCarrello(p3_4, 2); // 2 Tiramisù
            for (RigaCarrelloVirtuale riga : o2.getCarrello()) {
                riga.setOrdine(o2);
            }
            cliente1.addOrdineEffettuato(o2);
            r3.addOrdine_Ricevuti(o2);
            em.persist(o2);

            // Ordine 3: Cliente 2 da Sushi Zen, Inviato oggi (attivo)
            Ordine o3 = new Ordine("inviato", cliente2.getIndirizzo(), cliente2);
            o3.setData(LocalDate.now());
            o3.addPiattoAlCarrello(p2_1, 1); // 1 Box Sushi Misto
            o3.addPiattoAlCarrello(p2_3, 1); // 1 Tempura Uramaki
            for (RigaCarrelloVirtuale riga : o3.getCarrello()) {
                riga.setOrdine(o3);
            }
            cliente2.addOrdineEffettuato(o3);
            r2.addOrdine_Ricevuti(o3);

            Notifica n3 = new Notifica("Il tuo ordine è stato ricevuto dal ristorante Sushi Zen", "NON_LETTA");
            o3.setNotifica(n3);
            em.persist(o3);

            // Ordine 4: Cliente 2 da Trattoria Nonna Rosa, Evaso 10 giorni fa
            Ordine o4 = new Ordine("evaso", cliente2.getIndirizzo(), cliente2);
            o4.setData(LocalDate.now().minusDays(10));
            o4.addPiattoAlCarrello(p3_2, 2); // 2 Cacio e Pepe
            for (RigaCarrelloVirtuale riga : o4.getCarrello()) {
                riga.setOrdine(o4);
            }
            cliente2.addOrdineEffettuato(o4);
            r3.addOrdine_Ricevuti(o4);
            em.persist(o4);

            em.getTransaction().commit();
            System.out.println("Database popolato con successo!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Errore durante il seeding del database:");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void aggiungiRistoranteDemoSeMancante(EntityManager em, String nome, String descrizione, String via,
                                                  String civico, String citta, int cap, Ristoratore ristoratore,
                                                  Piatto... piatti) {
        Long presenti = em.createQuery("SELECT COUNT(r) FROM Ristorante r WHERE r.nome = :nome", Long.class)
                .setParameter("nome", nome)
                .getSingleResult();
        if (presenti > 0) {
            return;
        }

        Ristorante ristorante = new Ristorante(nome, descrizione, via, civico, citta, cap, "Lunedi", 12, 23,
                ristoratore);
        ristorante.addOrariApertura(new Data("Lunedi", 12, 15));
        ristorante.addOrariApertura(new Data("Lunedi", 19, 23));
        ristorante.addOrariApertura(new Data("Sabato", 19, 23));
        ristorante.addOrariApertura(new Data("Domenica", 19, 23));

        for (Piatto piatto : piatti) {
            piatto.setNomePiatto(nomePiattoLibero(em, piatto.getNomePiatto(), nome));
            ristorante.getMenu().add(piatto);
        }

        ristoratore.addRistorante(ristorante);
        em.persist(ristorante);
    }

    private String nomePiattoLibero(EntityManager em, String nomePiatto, String nomeRistorante) {
        Long presenti = em.createQuery("SELECT COUNT(p) FROM Piatto p WHERE p.nomePiatto = :nomePiatto", Long.class)
                .setParameter("nomePiatto", nomePiatto)
                .getSingleResult();
        if (presenti == 0) {
            return nomePiatto;
        }
        return nomePiatto + " - " + nomeRistorante;
    }
}
