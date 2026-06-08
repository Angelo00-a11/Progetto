package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rigaCarrelloVirtuale")
public class RigaCarrelloVirtuale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "idRigaCarrelloVirtuale",
                referencedColumnName = "idRigaCarrelloVirtuale",
                nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idPiatto", nullable = false)
    private Piatto piatto;

    @Column(name = "quantita", nullable = false)
    public int quantita;

    @ManyToOne
    @JoinColumn(name = "idOrdine", nullable = false)
    private Ordine ordine;

    // Costruttore senza parametri (richiesto da JPA)
    public RigaCarrelloVirtuale() {
    }

    public RigaCarrelloVirtuale(Piatto p, int quantita) {
        this.piatto = p;
        this.quantita = quantita;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Piatto getPiatto() {
        return piatto;
    }
    public void setPiatto(Piatto piatto) {
        this.piatto = piatto;
    }

    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Ordine getOrdine() {
        return ordine;
    }
    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RigaCarrelloVirtuale that = (RigaCarrelloVirtuale) o;

        return piatto.equals(that.piatto);
    }
    @Override
    public int hashCode() {
        return piatto.hashCode();
    }
}


