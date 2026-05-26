package Entity;

public class Data {

    private String giorno;
    private int inizioServizio;
    private int fineServizio;

    public Data(String giorno, int inizioServizio, int fineServizio){
        this.giorno=giorno;
        this.inizioServizio=inizioServizio;
        this.fineServizio=fineServizio;
    }

    public String getGiorno() {return giorno; }
    public int getInizioServizio() { return inizioServizio; }
    public int getFineServizio() { return fineServizio; }

    public void setInizioServizio(int inizioServizio) {this.inizioServizio = inizioServizio; }
    public void setFineServizio(int fineServizio) { this.fineServizio = fineServizio; }
    public void setGiorno(String giorno) { this.giorno = giorno; }
}
