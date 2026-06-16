package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "piatti")
public class Piatto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPiatti")
    private Long id;

    @Column(name = "nomePiatto", nullable = false, unique = true)
    private String nomePiatto;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo", nullable = false)
    private float prezzo;

    // Costruttore senza parametri (richiesto da JPA)
    public Piatto() {
    }

    public Piatto(String nomePiatto, String descrizione, float prezzo) {
        this.nomePiatto = nomePiatto;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public String getNomePiatto() {
        return nomePiatto;
    }
    public float getPrezzo() {
        return prezzo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setNomePiatto(String nomePiatto) {
        this.nomePiatto = nomePiatto;
    }
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return nomePiatto;
    }
}

