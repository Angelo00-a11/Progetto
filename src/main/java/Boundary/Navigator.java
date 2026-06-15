package Boundary;

import javax.swing.*;
import java.awt.*;

public class Navigator extends JFrame {
    private CardLayout layout;
    private JPanel container;
    private String nomeRistorante;
    private FormOrdinazione formOrdinazione;
    private FormListaRistoranti formListaRistoranti;
    private FormAmministratore formAmministratore;

    public Navigator(){
        layout = new CardLayout();
        container = new JPanel(layout);

        container.add(new FormHome(this).contentPane, "HOME");
        formListaRistoranti = new FormListaRistoranti(this);
        container.add(formListaRistoranti.contentPane, "LISTA_RISTORANTI");
        container.add(new FormGestioneRistorante(this).contentPane, "GESTIONE_RISTORANTE");
        formOrdinazione = new FormOrdinazione(this);
        container.add(formOrdinazione.contentPane, "ORDINAZIONE");
        container.add(new Login(this).getContentPane(), "LOGIN");
        container.add(new Register(this).getContentPane(), "REGISTER");
        container.add(new ModificaPiatto(this).getContentPane(), "MODIFICA_PIATTO");
        formAmministratore = new FormAmministratore(this);
        container.add(formAmministratore.contentPane, "MONITORA_SISTEMA");

        add(container);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        showHome();
    }

    public void showHome(){
        setTitle("Home");
        layout.show(container, "HOME");
    }
    public void showListaRistoranti(){
        setTitle("Lista Ristoranti");
        formListaRistoranti.caricaRistoranti();
        layout.show(container, "LISTA_RISTORANTI");
    }
    public void showGestioneRistorante(){
        setTitle("Gestione Ristorante");
        layout.show(container, "GESTIONE_RISTORANTE");
    }
    public void showOrdinazione(){
        setTitle("Ordinazione");
        formOrdinazione.setNomeRistorante_e_POPUP();
        layout.show(container, "ORDINAZIONE");
    }
    public void showLogin(){
        setTitle("Login");
        layout.show(container, "LOGIN");
    }
    public void showRegister(){
        setTitle("Register");
        layout.show(container, "REGISTER");
    }
    public void showModificaPiatto(){
        setTitle("Modifica Piatto");
        layout.show(container,"MODIFICA_PIATTO");
    }
    public void showMonitoraSistema(){
        setTitle("Monitoraggio Sistema");
        layout.show(container, "MONITORA_SISTEMA");
    }
    public void setNomeRistorante(String nome) {
        this.nomeRistorante = nome;
    }

    public String getNomeRistorante() {
        return nomeRistorante;
    }

    // Metodo generico e ricorsivo per pulire i campi di qualsiasi pannello/form
    public static void clearFields(Container container) {
        if (container == null) return;
        for (Component c : container.getComponents()) {
            if (c instanceof JTextField) ((JTextField) c).setText("");
            else if (c instanceof JComboBox && ((JComboBox<?>) c).getItemCount() > 0) ((JComboBox<?>) c).setSelectedIndex(0);
            else if (c instanceof Container) clearFields((Container) c);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Navigator::new);
    }
}
