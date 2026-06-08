package Database;

import Entity.Utente;

public class Session {
    private static Session instance;
    private Utente utenteLoggato;
    private Long idClienteLoggato;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }
    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public void setIdClienteLoggato(Long id) {
        this.idClienteLoggato = id;
    }
    public Long getIdClienteLoggato() {
        return idClienteLoggato;
    }

    public void logout() {
        this.utenteLoggato = null;
        this.idClienteLoggato = null;
    }
}

