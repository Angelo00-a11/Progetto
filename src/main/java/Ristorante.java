public class Ristorante {
    String nome;
    StringBuilder descrizione;
    Indirizzo indirizzoRistorante;
    Data[] orariApertura;
    Piatto[] Menu;

    Ristoratore ristoratoreResponsabile;

    public Ristorante(String nome, StringBuilder descrizione, String via, int civico, String citta, int cap, String giorno,int inizioServizio,int fineServizio){
        this.nome=nome;
        this.descrizione= new StringBuilder();
        this.indirizzoRistorante= new Indirizzo(via,civico,citta,cap);
        //Forse è meglio fare contenimento lasco//this.orariApertura[]=new Data(giorno,inizioServizio,fineServizio) ;
    }

}
