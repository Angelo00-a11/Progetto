public abstract class Utente {
    protected String nome;
    protected String cognome;
    protected  String email;
    protected Indirizzo indirizzo;
    protected String ruolo;

    public Utente(String nome, String cognome, String email, String ruolo,String via, int civico, String citta, int cap) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        this.indirizzo=new Indirizzo(via,civico,citta,cap);
    }

    public String getCognome() {
        return cognome;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }


    public void Register(String nome, String cognome, String email, String ruolo) {
       /* this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
        */
    }


    public abstract int Accedi(String nome,String cognome, String email);
}