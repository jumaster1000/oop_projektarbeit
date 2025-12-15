import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class Leitstellensystem extends JFrame {
    // ==============================
    // Attribute Initialisieren
    // ==============================
    // Haupt Panel
    private JPanel hauptPanel;

    // Einsatz anlegen
        // Adresse
    private JLabel einsatzAnlegenLabel;
    private JLabel adresseLabel;
    private JTextField adresseTextField;
    private JLabel hNrLabel;
    private JTextField hNrTextField;
    private JLabel postleitzahlLabel;
    private JTextField plzTextField;
    private JLabel ortLabel;
    private JTextField ortTextField;

        // Zusatzinformationen
    private JLabel bemerkungLabel;
    private JTextField bemerkungTextField;
    private JLabel MiGLabel;
    private JCheckBox miGCheckBox;
    private JLabel stichwortLabel;
    private JComboBox stichwortComboBox;
    private JLabel signalfahrtLabel;
    private JCheckBox signalfahrtCheckBox;

        // Buttons
    private JButton alarmierenButton;
    private JButton eingabeLoeschen;

        // Einsatzverwaltung
    private JLabel laufendeEinsaetzeLabel;
    private JTable einsatzTable;
    private DefaultTableModel tableModel;
    private ArrayList<Einsatz> einsatzListe;

        // Filter
    private JLabel fiterLabel;
    private JTextField filterTextField;
    private JButton filterButton;
    private JComboBox filternComboBox;
    private JButton ausgewählteEinsätzeBeendenButton;


    // Logo
    private JLabel leitstelleIcon;

    // ==============================
    // Konstruktor
    // ==============================
    public Leitstellensystem(ArrayList<Einsatz> einsatzListe){
        this.einsatzListe = einsatzListe;

        // Spalten definieren
        String[] columnNames = {
                "Stichwort", "Adresse", "Bemerkung", "Ort", "MiG", "Signalfahrt"
        };

        // TableModel bauen
        tableModel = new DefaultTableModel(columnNames, 0);

        // Das Model an deine JTable aus dem Designer geben
        einsatzTable.setModel(tableModel);
        einsatzTable.setDefaultEditor(Object.class, null);

        // Spaltenbreiten anpassen
        einsatzTable.getColumnModel().getColumn(0).setPreferredWidth(150);  // Stichwort
        einsatzTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Adresse
        einsatzTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Bemerkung
        einsatzTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Ort
        einsatzTable.getColumnModel().getColumn(4).setPreferredWidth(15);   // MiG
        einsatzTable.getColumnModel().getColumn(5).setPreferredWidth(30);   // Signalfahrt

        // Demo-Einsätze
        for (Einsatz e : einsatzListe) {
            tableModel.addRow(new Object[]{
                    e.getStichwort(),
                    e.getAdresse() + " " + e.getHausNr(),
                    e.getBemerkung(),
                    e.getOrt() + " (" + e.getPlz() + ")",
                    e.getMiG() ? "Ja" : "Nein",
                    e.getSignalfahrt() ? "Ja" : "Nein"
            });
        }

        // Rest des Konstruktors
        setTitle("Leitstellensystem - Leitstelle Ulm / Neu-Ulm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(hauptPanel);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        // ==============================
        // Action Listener
        // ==============================
        alarmierenButton.addActionListener(e -> alarmieren());

        eingabeLoeschen.addActionListener(e -> { eingabeLoeschen();});

        filterButton.addActionListener(e -> { filtern();});
    }

    // ==============================
    // Button Eingabe Löschen
    // ==============================
    public void eingabeLoeschen(){
        adresseTextField.setText("");
        hNrTextField.setText("");
        plzTextField.setText("");
        ortTextField.setText("");
        bemerkungTextField.setText("");
        miGCheckBox.setSelected(false);
        stichwortComboBox.setSelectedItem("- Stichwort auswählen -");
        signalfahrtCheckBox.setSelected(false);
    }


    // ==============================
    // Button Alarmieren
    // ==============================
    public void alarmieren(){
        try {
            // --- Daten aus dem Formular abspeichern ---
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

            // --- Prüfen, ob die Eingaben den Vorgaben entsprechen ---
            if (adresse.isEmpty() || hausNr.isEmpty() || plz <= 0 || ort.isEmpty()) {
                throw new IllegalArgumentException("Fülle alle Felder aus");
            }

            if (stichwort.equals("- Stichwort auswählen -")) {
                throw new IllegalArgumentException("Wähle ein Stichwort aus");
            }

            // --- Objekt erstellen -> siehe Klasse 'Einsatz' ---
            Einsatz einsatz = new Einsatz(adresse, hausNr, plz, ort, bemerkung, miG, stichwort, signalfahrt);

            // --- Objekt in Array Liste speichern ---
            einsatzListe.add(einsatz);

            // --- In die Tabelle einfügen ---
            tableModel.addRow(new Object[]{
                    einsatz.getStichwort(),
                    einsatz.getAdresse() + " " + einsatz.getHausNr(),
                    einsatz.getBemerkung(),
                    einsatz.getOrt() + " (" + einsatz.getPlz() + ")",
                    einsatz.getMiG() ? "Ja" : "Nein",
                    einsatz.getSignalfahrt() ? "Ja" : "Nein"
            });

            // --- Eingabefelder zurücksetzen ---
            eingabeLoeschen();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==============================
    // Einsätze filtern
    // ==============================
    public void filtern(){
        try {
            // --- Daten aus der Filter-Option abspeichern ---
            String spalte = filternComboBox.getSelectedItem().toString();
            String suchbegriff = filterTextField.getText();

            // --- Prüfen, ob die Eingaben den Vorgaben entsprechen ---
            if (spalte.equals("- Spalte auswählen")) {
                throw new IllegalArgumentException("Bitte Spalte auswählen");
            }
            if (suchbegriff.equals("")) {
                throw new IllegalArgumentException("Bitte Suchbegriff eingeben");
            }

            // --- Wechsel Button Text - Filter setzen / Filter löschen ---
            if (filterButton.getText().equals("Filter setzen")) {
                filterButton.setText("Filter löschen");
            } else if (filterButton.getText().equals("Filter löschen")) {
                filternComboBox.setSelectedItem("- Spalte auswählen");
                filterTextField.setText("");
                filterButton.setText("Filter setzen");
            }

        } catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==============================
    // MAIN-Methode
    // ==============================
    public static void main(String[] args) {
        ArrayList<Einsatz> startListe = new ArrayList<>();

        startListe.add(new Einsatz(
                "Münchner Straße", "12", 89073, "Ulm",
                "Nachbar sieht Rauch", false,
                "B2 – Rauchentwicklung", true
        ));

        startListe.add(new Einsatz(
                "Karlstraße", "5", 89231, "Neu-Ulm",
                "VU mit PKW", false,
                "THL 2 – Verkehrsunfall", false
        ));

        startListe.add(new Einsatz(
                "Söflinger Straße", "102", 89073, "Ulm",
                "Mehrere Anrufe; Flammen sichtbar", true,
                "B3 - Dachstuhlbrand", true
        ));

        new Leitstellensystem(startListe);
    }
}
