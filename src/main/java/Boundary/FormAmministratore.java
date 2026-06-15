package Boundary;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import Controller.AmministratoreController;


public class FormAmministratore {
    JPanel contentPane;
    private JPanel Header;
    private JPanel Body;
    private JPanel Footer;
    private JPanel selezioneDate;
    private JTextField txtDataInizio;
    private JTextField txtDataFine;
    private JButton btnAvviaMonitoraggio;
    private JPanel risultatiNumerici;
    private JScrollPane risultatiRistoranti;
    private JButton btnTerminaMonitoraggio;
    private JTable tblRistoranti;
    private JLabel lblTotOrdini;
    private JLabel lblVolumeOrdini;
    private JPanel input;

    private final Navigator navigator;

    public FormAmministratore(Navigator navigator) {
        this.navigator = navigator;

        // IMPOSTA LO STATO DI DEFAULT PRIMA DEL CALCOLO
        lblTotOrdini.setText("---");
        lblVolumeOrdini.setText("---");

        // INIZIALIZZA LA TABELLA VUOTA CON LE SOLE INTESTAZIONI
        String[] colonne = {"Nome Ristorante", "Ristoratore Responsabile", "Numero Ordini"};
        // Modello con 0 righe iniziali
        tblRistoranti.setModel(new DefaultTableModel(colonne, 0));

        //bottone AVVIA MONITORAGGIO
        btnAvviaMonitoraggio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avviaMonitoraggio();
            }
        });

        //bottone TERMINA MONITORAGGIO
        btnTerminaMonitoraggio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tornaHome();
            }
        });
    }

    //metodo privato bottone avviaMonitoraggio
    private void avviaMonitoraggio() {
        //estraggo testo dalle JTextField
        String strDataInizio = txtDataInizio.getText();
        String strDataFine = txtDataFine.getText();

        //controllo validità input --> se è vuoto o è nel setting di default [ gg/mm/aaaa ]
        if (strDataInizio.isEmpty() || strDataFine.isEmpty() ||
                strDataInizio.equalsIgnoreCase("gg/mm/aaaa") || strDataFine.equalsIgnoreCase("gg/mm/aaaa")) {
            JOptionPane.showMessageDialog(null, "compilare i campi data",
                    "Errore Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            //chiamata metodo monitoraSistema del CONTROLLER
            Map<String, Object> statistiche = AmministratoreController.monitoraSistema(strDataInizio, strDataFine);

            //estrazione statistiche ottenute
            int totOrdini = (int) statistiche.get("totaleOrdini");
            double volOrdini = (double) statistiche.get("volumeOrdini");
            List<Object[]> ristPiuAttivi = (List<Object[]>) statistiche.get("ristPiuAttivi");

            //aggiorna schermata con statistiche numeriche
            lblTotOrdini.setText(String.valueOf(totOrdini));
            lblVolumeOrdini.setText(String.format("%.2f €", volOrdini));

            //popola la JTable dei Ristoranti
            String[] colonne = {"Nome Ristorante", "Ristoratore Responsabile", "Numero Ordini"};
            DefaultTableModel model = new DefaultTableModel(colonne, 0);

            // Inserisce ogni array di Object come riga della tabella
            for (Object[] riga : ristPiuAttivi) {
                model.addRow(riga);
            }

            // Imposta il modello aggiornato sulla JTable dello schermo
            tblRistoranti.setModel(model);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'elaborazione: " + ex.getMessage(),
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
        }


    }

    //metodo privato bottone terminaMonitoraggio
    private void tornaHome() {
        navigator.showHome();
        resetForm();
    }

    //metodo privato per riportare la schermata allo stato iniziale
    private void resetForm() {
        //ripristina i campi di testo
        txtDataInizio.setText("gg/mm/aaaa");
        txtDataFine.setText("gg/mm/aaaa");

        //ripristina le label numeriche
        lblTotOrdini.setText("---");
        lblVolumeOrdini.setText("---");

        //svuota la tabella mantenendo solo le intestazioni
        String[] colonne = {"Nome Ristorante", "Ristoratore Responsabile", "Numero Ordini"};
        tblRistoranti.setModel(new DefaultTableModel(colonne, 0));
    }

//prova 

/*
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Monitoraggio Sistema");
        frame.setContentPane(new FormAmministratore(new Navigator()).contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
*/




    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        Header = new JPanel();
        Header.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(Header, BorderLayout.NORTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        Header.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Monitoraggio Sistema");
        panel1.add(label1, BorderLayout.NORTH);
        selezioneDate = new JPanel();
        selezioneDate.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(selezioneDate, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        label2.setText("Data inizio:");
        selezioneDate.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(82, 17), null, 0, false));
        txtDataInizio = new JTextField();
        Font txtDataInizioFont = this.$$$getFont$$$(null, Font.ITALIC, -1, txtDataInizio.getFont());
        if (txtDataInizioFont != null) txtDataInizio.setFont(txtDataInizioFont);
        txtDataInizio.setText("gg/mm/aaaa");
        txtDataInizio.setToolTipText("");
        selezioneDate.add(txtDataInizio, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(60, -1), null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Data fine:");
        selezioneDate.add(label3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        txtDataFine = new JTextField();
        Font txtDataFineFont = this.$$$getFont$$$(null, Font.ITALIC, -1, txtDataFine.getFont());
        if (txtDataFineFont != null) txtDataFine.setFont(txtDataFineFont);
        txtDataFine.setHorizontalAlignment(11);
        txtDataFine.setText("gg/mm/aaaa");
        selezioneDate.add(txtDataFine, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(60, -1), null, null, 0, false));
        btnAvviaMonitoraggio = new JButton();
        btnAvviaMonitoraggio.setText("Avvia Monitoraggio");
        selezioneDate.add(btnAvviaMonitoraggio, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        Header.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Body = new JPanel();
        Body.setLayout(new BorderLayout(0, 0));
        contentPane.add(Body, BorderLayout.CENTER);
        risultatiNumerici = new JPanel();
        risultatiNumerici.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        Body.add(risultatiNumerici, BorderLayout.NORTH);
        final JLabel label4 = new JLabel();
        label4.setText("Totale ordini:");
        risultatiNumerici.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Volume medio:");
        risultatiNumerici.add(label5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTotOrdini = new JLabel();
        Font lblTotOrdiniFont = this.$$$getFont$$$(null, -1, 18, lblTotOrdini.getFont());
        if (lblTotOrdiniFont != null) lblTotOrdini.setFont(lblTotOrdiniFont);
        lblTotOrdini.setText("");
        risultatiNumerici.add(lblTotOrdini, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblVolumeOrdini = new JLabel();
        Font lblVolumeOrdiniFont = this.$$$getFont$$$(null, -1, 18, lblVolumeOrdini.getFont());
        if (lblVolumeOrdiniFont != null) lblVolumeOrdini.setFont(lblVolumeOrdiniFont);
        lblVolumeOrdini.setText("");
        risultatiNumerici.add(lblVolumeOrdini, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        risultatiNumerici.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        risultatiNumerici.add(spacer3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        risultatiRistoranti = new JScrollPane();
        Body.add(risultatiRistoranti, BorderLayout.CENTER);
        tblRistoranti = new JTable();
        tblRistoranti.setEnabled(true);
        risultatiRistoranti.setViewportView(tblRistoranti);
        Footer = new JPanel();
        Footer.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(Footer, BorderLayout.SOUTH);
        btnTerminaMonitoraggio = new JButton();
        btnTerminaMonitoraggio.setText("Termina monitoraggio");
        Footer.add(btnTerminaMonitoraggio, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        Footer.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
