import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Leitstellensystem extends JFrame {
    // Haupt Panel
    private JPanel hauptPanel;

    // Einsatz anlegen
        // Adresse
    private JLabel einsatzAnlegenLabel;
    private JLabel adresseLabel;
    private JTextField adresseTextField;
    private JLabel hNrLabel;
    private JLabel postleitzahlLabel;
    private JTextField plzTextField;
    private JLabel ortLabel;
    private JTextField ortTextField;
        // Zusatzinformationen
    private JLabel bemerkungLabel;
    private JTextField bemerkungTextField;
    private JTextField hNrTextField;
    private JLabel MiGLabel;
    private JCheckBox miGCheckBox;
    private JLabel stichwortLabel;
    private JComboBox stichwortComboBox;
    private JLabel signalfahrtLabel;
    private JCheckBox signalfahrtCheckBox;
    private JButton alarmierenButton;
    private JButton eingabeLoeschen;
    private JTextArea bemerkungTextArea;
    private JLabel laufendeEinsaetzeLabel;
    private JTable einsatzTable;
    private JLabel fiterLabel;
    private JTextField filterTextField;
    private JButton filterButton;
    private JLabel leitstelleIcon;
    private JComboBox filternComboBox;


    private DefaultTableModel tableModel;

    private ArrayList<Einsatz> einsatzListe = new ArrayList<>();



    public Leitstellensystem(){

        // 1. Spalten definieren
        String[] columnNames = {
                "Stichwort", "Adresse", "Bemerkung", "Ort", "MiG", "Signalfahrt"
        };

        // 2. TableModel bauen
        tableModel = new DefaultTableModel(columnNames, 0);

        // 3. Das Model an deine JTable aus dem Designer geben
        einsatzTable.setModel(tableModel);
        einsatzTable.setDefaultEditor(Object.class, null);

        // Spaltenbreiten anpassen; Summe 890
        einsatzTable.getColumnModel().getColumn(0).setPreferredWidth(170);  // Stichwort
        einsatzTable.getColumnModel().getColumn(1).setPreferredWidth(230);  // Adresse
        einsatzTable.getColumnModel().getColumn(2).setPreferredWidth(250);  // Bemerkung
        einsatzTable.getColumnModel().getColumn(3).setPreferredWidth(40);  // Ort
        einsatzTable.getColumnModel().getColumn(4).setPreferredWidth(15);   // MiG
        einsatzTable.getColumnModel().getColumn(5).setPreferredWidth(30);   // Signalfahrt

        // 4. Rest des Constructors wie gehabt
        setTitle("Leitstellensystem - Leitstelle Ulm / Neu-Ulm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(hauptPanel);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        alarmierenButton.addActionListener(e -> alarmieren());

        eingabeLoeschen.addActionListener(e -> {
            adresseTextField.setText("");
            hNrTextField.setText("");
            plzTextField.setText("");
            ortTextField.setText("");
            bemerkungTextField.setText("");
            miGCheckBox.setSelected(false);
            stichwortComboBox.setSelectedItem("- Stichwort auswählen -");
            signalfahrtCheckBox.setSelected(false);
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtern();
            }
        });
    }

    public void alarmieren(){
        try {

            String adresse = adresseTextField.getText();
            String hausNr = hNrTextField.getText();
            int plz = Integer.parseInt(plzTextField.getText());
            String ort = ortTextField.getText();
            String bemerkung = bemerkungTextField.getText();
            if (bemerkung.equals("- hier Bemerkung einfügen -")) {
                bemerkung = "- keine Angabe";
            }
            boolean miG;
            if (miGCheckBox.isSelected()) {
                miG = true;
            } else miG = false;
            String stichwort = stichwortComboBox.getSelectedItem().toString();
            boolean signalfahrt;
            if (signalfahrtCheckBox.isSelected()) {
                signalfahrt = true;
            } else signalfahrt = false;

            if (adresse.isEmpty() || hausNr.isEmpty() || plz <= 0 || ort.isEmpty()) {
                throw new IllegalArgumentException("Fülle alle Felder aus");
            }

            if (stichwort.equals("- Stichwort auswählen -")) {
                throw new IllegalArgumentException("Wähle ein Stichwort aus");
            }


            Einsatz einsatz = new Einsatz(adresse, hausNr, plz, ort, bemerkung, miG, stichwort, signalfahrt);

            einsatzListe.add(einsatz);

            // In die Tabelle einfügen
            tableModel.addRow(new Object[]{
                    einsatz.getStichwort(),
                    einsatz.getAdresse() + " " + einsatz.getHausNr(),
                    einsatz.getBemerkung(),
                    einsatz.getOrt() + " (" + einsatz.getPlz() + ")",
                    einsatz.getMiG() ? "Ja" : "Nein",
                    einsatz.getSignalfahrt() ? "Ja" : "Nein"
            });

            adresseTextField.setText("");
            hNrTextField.setText("");
            plzTextField.setText("");
            ortTextField.setText("");
            bemerkungTextArea.setText("- hier Bemerkung einfügen -");
            miGCheckBox.setSelected(false);
            stichwortComboBox.setSelectedItem("- Stichwort auswählen -");
            signalfahrtCheckBox.setSelected(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filtern(){
        if (filterButton.getText().equals("Filter setzen")) {
            filterButton.setText("Filter löschen");
        } else if (filterButton.getText().equals("Filter löschen")) {
            filternComboBox.setSelectedItem("- Spalte auswählen");
            filterTextField.setText("");
            filterButton.setText("Filter setzen");
        }
    }

    public static void main(String[] args) {
        new Leitstellensystem();
    }
}
