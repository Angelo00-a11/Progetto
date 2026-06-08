package Boundary;

import javax.swing.*;
import java.awt.*;

public class Navigator extends JFrame {
    private CardLayout layout;
    private JPanel container;
    private String nomeRistorante;
    private FormOrdinazione formOrdinazione;

    public Navigator(){
        layout = new CardLayout();
        container = new JPanel(layout);

        container.add(new FormHome(this).contentPane, "HOME");
        container.add(new FormListaRistoranti(this).contentPane, "LISTA_RISTORANTI");
        container.add(new FormGestioneRistorante(this).contentPane, "GESTIONE_RISTORANTE");
        formOrdinazione = new FormOrdinazione(this);
        container.add(formOrdinazione.contentPane, "ORDINAZIONE");
        container.add(new Login(this).getContentPane(), "LOGIN");
        container.add(new Register(this).getContentPane(), "REGISTER");
        container.add(new ModificaPiatto(this).getContentPane(), "MODIFICA_PIATTO");

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

    public void setNomeRistorante(String nome) {
        this.nomeRistorante = nome;
    }

    public String getNomeRistorante() {
        return nomeRistorante;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Navigator::new);
    }
}
