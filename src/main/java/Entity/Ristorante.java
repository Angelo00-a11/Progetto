package Entity;

import java.util.ArrayList;
import java.util.Scanner;

public class Ristorante {
    private String nome;
    private StringBuilder descrizione;
    private Indirizzo indirizzoRistorante;
    private ArrayList<Data> orariApertura;
    private ArrayList<Piatto> Menu;

    private ArrayList<Ordine> ordiniRicevuti;

    private Ristoratore ristoratoreResponsabile;

    public Ristorante(String nome, StringBuilder descrizione, String via, int civico, String citta, int cap, String giorno,int inizioServizio,int fineServizio){
        this.nome=nome;
        this.descrizione= new StringBuilder();
        this.indirizzoRistorante= new Indirizzo(via,civico,citta,cap);
        this.orariApertura=new ArrayList<>();
        this.Menu=new ArrayList<>();
    }


    public void CreaMenu(){
        ArrayList<Piatto> menu=new ArrayList<>();
        System.out.println("Quanti piatti vuoi inserire?");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i=0;i<n;i++) {
            System.out.println("Inserisci il nome del piatto:");
            String nome = sc.nextLine();
            System.out.println("Inserisci la descrizione del piatto:");
            String descrizione = sc.next();
            System.out.println("Inserisci il prezzo del piatto:");
            int prezzo = sc.nextInt();
            Piatto p1 = new Piatto(nome, descrizione, prezzo);
            menu.add(p1);
        }
        Menu.addAll(menu);
        menu.clear();;
    }
    public void ModificaMenu(ArrayList<Piatto> p,int command){
        if(command==1)
            Menu.addAll(p);
        if(command==2){
            Menu.removeAll(p);
        }
        if(command==-1){
            Menu.clear();
        }
    }
    public void VisualizzaMenu(){
        System.out.println(Menu);
    }




    void addOrariApertura(Data d){
        orariApertura.add(d);
    }
    void removeOrariApertura(Data d){
        orariApertura.remove(d);
    }

    void addOrdine_Ricevuti(Ordine o){
        ordiniRicevuti.add(o);
    }

}
