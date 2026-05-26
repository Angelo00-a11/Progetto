import Entity.Amministratore;
import Entity.Cliente;
import Entity.Ristoratore;

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

    Cliente c;
    Ristoratore r;
    Amministratore a;

    if (ruolo.equalsIgnoreCase("Entity.Cliente")) {
        c = new Cliente(nome, cognome, email, ruolo, via,civico,citta,cap);
        c.Register(nome, cognome, email, ruolo);
    }
    if (ruolo.equalsIgnoreCase("Entity.Ristoratore")) {
        r = new Ristoratore(nome, cognome, email, ruolo, via,civico,citta,cap);
        r.Register(nome, cognome, email, ruolo);
    }
    if (ruolo.equalsIgnoreCase("Entity.Amministratore")) {
        a = new Amministratore(nome, cognome, email, ruolo, via,civico,citta,cap);
        a.Register(nome, cognome, email, ruolo);
    }

    //int res = c.Ordina(new Entity.Ordine(null,c.indirizzo,null));
}