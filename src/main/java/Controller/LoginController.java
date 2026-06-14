package Controller;

import Database.Session;
import Entity.*;

import java.util.List;

public class LoginController {

    public static boolean login(String email,String ruolo){

        boolean esito = false;
        GestoreUtenti gu = new GestoreUtenti();
        List<Utente> l =gu.cercaUtentePerEmail(email);
        if (l.isEmpty()) {return esito;}
        else if(l.getFirst() instanceof Cliente && ruolo.equalsIgnoreCase("CLIENTE")){
            Session.getInstance().setUtenteLoggato(l.getFirst());
            return true;
        }
        else if(l.getFirst() instanceof Ristoratore && ruolo.equalsIgnoreCase("RISTORATORE")){
            Session.getInstance().setUtenteLoggato(l.getFirst());
            return true;
        }
        else if(l.getFirst() instanceof Amministratore && ruolo.equalsIgnoreCase("AMMINISTRATORE")){
            Session.getInstance().setUtenteLoggato(l.getFirst());
            return true;
        }
        return false;
    }
}