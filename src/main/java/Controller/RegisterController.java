package Controller;

import Database.Session;
import Entity.Cliente;

public class RegisterController {

    public static boolean register(String ruolo){
        if (ruolo.equalsIgnoreCase("Cliente")) {
            Cliente c = new Cliente("Mario", "Rossi", "cliente@test.it", "Cliente", "ViaRoma", 1, "Roma", 12345); // ci andrebbero tutti i vri attributi di cliente
            new Database.GestorePersistenza().salva(c);
            Session.getInstance().setUtenteLoggato(c);
            return true;
        } else if (ruolo.equalsIgnoreCase("Ristoratore")) {
            // Register restaurant owner
        }
        return false;
    }
}
