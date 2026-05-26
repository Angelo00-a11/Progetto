package Entity;

public class Piatto {
    String nomePiatto;
    StringBuilder descrizione;
    int prezzo;

    protected Piatto(String nomePiatto,StringBuilder descrizione,int prezzo){
        this.nomePiatto=nomePiatto;
        this.descrizione=new StringBuilder();
        this.prezzo=prezzo;
    }

    public StringBuilder getDescrizione() { return descrizione; }
    public String getNomePiatto() {
        return nomePiatto;
    }
    public int getPrezzo() { return prezzo; }

    public void setDescrizione(StringBuilder descrizione) {
        this.descrizione = descrizione;
    }
    public void setNomePiatto(String nomePiatto) { this.nomePiatto = nomePiatto; }
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
}
