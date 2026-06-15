package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utenti")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ruolo", discriminatorType = DiscriminatorType.STRING)
public abstract class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "ruolo", nullable = false, insertable = false, updatable = false)
    private String ruolo;

    @ManyToOne(cascade = CascadeType.PERSIST) // MODIFICATO QUI
    @JoinColumn(name = "indirizzo",
                referencedColumnName = "idIndirizzo",
                nullable = false)
    private Indirizzo indirizzo;

    // Costruttore senza parametri (richiesto da JPA)
    public Utente() {
    }

    public Utente(String nome, String cognome, String email, String ruolo, String via, String civico, int cap, String citta) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        this.indirizzo = new Indirizzo(via, civico, cap, citta);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getRuolo() {
        return ruolo;
    }
    public Indirizzo getIndirizzo() { // Modificato il tipo di ritorno da String a Indirizzo
        return indirizzo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void Register(String nome, String cognome, String email, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public abstract int Accedi(String nome, String cognome, String email);

}

