package Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "ristoranti")
public class Ristorante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idIndirizzoRistorante", referencedColumnName = "idIndirizzo")
    private Indirizzo indirizzoRistorante;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ristorante_id")
    private List<Data> orariApertura;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ristorante_id")
    private List<Piatto> menu;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ristorante_id")
    private List<Ordine> ordiniRicevuti;

    @ManyToOne
    @JoinColumn(name = "ristoratore_id", referencedColumnName = "id", nullable = false)
    private Ristoratore ristoratoreResponsabile;

    // Costruttore senza parametri (richiesto da JPA)
    public Ristorante() {
    }

    public Ristorante(String nome, String descrizione, String via, String civico, String citta, int cap, String giorno, int inizioServizio, int fineServizio, Ristoratore r) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.indirizzoRistorante = new Indirizzo(via, civico, cap, citta);
        this.orariApertura = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.ordiniRicevuti = new ArrayList<>();
        this.ristoratoreResponsabile = r;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public Indirizzo getIndirizzoRistorante() {
        return indirizzoRistorante;
    }

    public void setIndirizzoRistorante(Indirizzo indirizzoRistorante) {
        this.indirizzoRistorante = indirizzoRistorante;
    }

    public List<Data> getOrariApertura() {
        return orariApertura;
    }
    public void setOrariApertura(List<Data> orariApertura) {
        this.orariApertura = orariApertura;
    }

    public List<Piatto> getMenu() {
        return menu;
    }
    public void setMenu(List<Piatto> menu) {
        this.menu = menu;
    }

    public List<Ordine> getOrdiniRicevuti() {
        return ordiniRicevuti;
    }
    public void setOrdiniRicevuti(List<Ordine> ordiniRicevuti) {
        this.ordiniRicevuti = ordiniRicevuti;
    }

    public Ristoratore getRistoratoreResponsabile() {
        return ristoratoreResponsabile;
    }
    public void setRistoratoreResponsabile(Ristoratore ristoratoreResponsabile) {
        this.ristoratoreResponsabile = ristoratoreResponsabile;
    }

    public void CreaMenu() {
        ArrayList<Piatto> tempMenu = new ArrayList<>();
        System.out.println("Quanti piatti vuoi inserire?");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Inserisci il nome del piatto:");
            String nome = sc.nextLine();
            System.out.println("Inserisci la descrizione del piatto:");
            String desc = sc.next();
            System.out.println("Inserisci il prezzo del piatto:");
            float prezzo = sc.nextFloat();
            Piatto p1 = new Piatto(nome, desc, prezzo);
            tempMenu.add(p1);
        }
        menu.addAll(tempMenu);
        tempMenu.clear();
    }

    public void ModificaMenu(ArrayList<Piatto> p, int command) {
        if (command == 1) {menu.addAll(p);}
        if (command == 2) {menu.removeAll(p);}
        if (command == -1) {menu.clear();}
    }

    public void addOrariApertura(Data d) {orariApertura.add(d);}

    public void removeOrariApertura(Data d) {orariApertura.remove(d);}

    public void addOrdine_Ricevuti(Ordine o) {ordiniRicevuti.add(o);}

}
