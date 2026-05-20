public class Piatto {
    String nomePiatto;
    StringBuilder descrizione;
    int prezzo;

    protected Piatto(String nomePiatto,StringBuilder descrizione,int prezzo){
        this.nomePiatto=nomePiatto;
        this.descrizione=new StringBuilder();
        this.prezzo=prezzo;
    }

    public StringBuilder getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(StringBuilder descrizione) {
        this.descrizione = descrizione;
    }
    public String getNomePiatto() {
        return nomePiatto;
    }
    public void setNomePiatto(String nomePiatto) {
        this.nomePiatto = nomePiatto;
    }
    public int getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
}
