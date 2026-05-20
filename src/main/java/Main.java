


void main() {

    System.out.println("Inserisci le credenziali:");
    Scanner sc = new Scanner(System.in);
    String nome = sc.nextLine();
    String cognome = sc.nextLine();
    String email = sc.nextLine();
    String ruolo = sc.nextLine();
    String via = sc.nextLine();
    int civico = sc.nextInt();
    String citta = sc.nextLine();
    int cap = sc.nextInt();

    if (ruolo.equalsIgnoreCase("Cliente")) {
        Cliente c = new Cliente(nome, cognome, email, ruolo, via,civico,citta,cap);
        c.Register(nome, cognome, email, ruolo);
    }
    if (ruolo.equalsIgnoreCase("Ristoratore")) {
        Ristoratore r = new Ristoratore(nome, cognome, email, ruolo, via,civico,citta,cap);
        r.Register(nome, cognome, email, ruolo);
    }
    if (ruolo.equalsIgnoreCase("Amministratore")) {
        Amministratore a = new Amministratore(nome, cognome, email, ruolo, via,civico,citta,cap);
        a.Register(nome, cognome, email, ruolo);
    }
}