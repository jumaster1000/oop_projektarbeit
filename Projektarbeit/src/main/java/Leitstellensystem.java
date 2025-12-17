import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;


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
    private ArrayList<Einsatz> einsatzListe = new ArrayList<>();;


    // Filter
    private JLabel fiterLabel;
    private JTextField filterTextField;
    private JButton filterButton;
    private JComboBox filternComboBox;
    private JButton einsaetzeBeendenButton;
    private TableRowSorter<DefaultTableModel> sorter;



    // Logo
    private JLabel leitstelleIcon;

    // ==============================
    // Konstruktor
    // ==============================
    public Leitstellensystem(){
        initObjekte();

        // Spalten definieren
        String[] columnNames = {
                "Stichwort", "Adresse", "Bemerkung", "Ort", "MiG", "Signalfahrt"
        };

        // TableModel bauen
        tableModel = new DefaultTableModel(columnNames, 0);

        // Das Model an deine JTable aus dem Designer geben
        einsatzTable.setModel(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        einsatzTable.setRowSorter(sorter);
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
        einsaetzeBeendenButton.addActionListener(e -> { einsaetzeBeenden();});
    }

    public void initObjekte(){
        einsatzListe.add(new Einsatz(
                "Münchner Straße", "12", 89073, "Ulm",
                "Nachbar sieht Rauch", false,
                "B2 – Rauchentwicklung", true
        ));

        einsatzListe.add(new Einsatz(
                "Karlstraße", "5", 89231, "Neu-Ulm",
                "VU mit PKW", false,
                "THL 2 – Verkehrsunfall", false
        ));

        einsatzListe.add(new Einsatz(
                "Söflinger Straße", "102", 89073, "Ulm",
                "Mehrere Anrufe; Flammen sichtbar", true,
                "B3 - Dachstuhlbrand", true
        ));
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
            String ort = ortTextField.getText();

            // --- Pflichtfelder prüfen (leer?) ---
            if (adresse.isEmpty() || hausNr.isEmpty() || ort.isEmpty() || plzTextField.getText().isEmpty()) {
                throw new IllegalArgumentException("Fülle alle Felder aus");
            }

            // PLZ Prüfung
            if (plzTextField.getText().isEmpty()) {
                throw new IllegalArgumentException("Fülle alle Felder aus");
            }

            for (char c : plzTextField.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    throw new IllegalArgumentException("PLZ muss eine Zahl sein");
                }
            }

            if (plzTextField.getText().length() != 5) {
                throw new IllegalArgumentException("PLZ muss 5-stellig sein");
            }


            int plz = Integer.parseInt(plzTextField.getText());

            // --- Weitere Felder ---
            String bemerkung = bemerkungTextField.getText();
            if (bemerkung.equals("- hier Bemerkung einfügen -")) {
                bemerkung = "- keine Angabe";
            }

            boolean miG = miGCheckBox.isSelected();
            boolean signalfahrt = signalfahrtCheckBox.isSelected();

            String stichwort = stichwortComboBox.getSelectedItem().toString();
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
    public void filtern() {
        try {

            // ===== FALL 1: Filter ist aktiv → Filter löschen =====
            if (filterButton.getText().equals("Filter löschen")) {

                sorter.setRowFilter(null); // Filter entfernen
                filternComboBox.setSelectedItem("- Spalte auswählen");
                filterTextField.setText("");
                filterButton.setText("Filter setzen");
                einsaetzeBeendenButton.setText("Alle Einsätze beenden");

                return;
            }

            // ===== FALL 2: Filter setzen =====
            String spalte = filternComboBox.getSelectedItem().toString();
            String suchbegriff = filterTextField.getText().trim();

            // --- Eingaben prüfen ---
            if (spalte.equals("- Spalte auswählen")) {
                throw new IllegalArgumentException("Bitte Spalte auswählen");
            }

            if (suchbegriff.equals("")) {
                throw new IllegalArgumentException("Bitte Suchbegriff eingeben");
            }

            int columnIndex;

            switch (spalte) {
                case "Stichwort" -> columnIndex = 0;
                case "Adresse" -> columnIndex = 1;
                case "Bemerkung" -> columnIndex = 2;
                case "Ort" -> columnIndex = 3;
                case "MiG" -> columnIndex = 4;
                case "Signalfahrt" -> columnIndex = 5;
                default -> throw new IllegalArgumentException("Ungültige Spalte");
            }

            // --- Filter anwenden (case-insensitive) ---
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + suchbegriff, columnIndex));

            // --- Button-Zustände aktualisieren ---
            filterButton.setText("Filter löschen");
            einsaetzeBeendenButton.setText("Ausgewählte Einsätze beenden");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==============================
    // Einsätze beenden
    // ==============================
    public void einsaetzeBeenden() {
        try {

            // ===== FALL 1: Kein Filter aktiv → alle Einsätze löschen =====
            if (filterButton.getText().equals("Filter setzen")) {

                if (einsatzListe.isEmpty()) {
                    throw new IllegalArgumentException("Keine Einsätze vorhanden");
                }

                einsatzListe.clear();
                tableModel.setRowCount(0);

                JOptionPane.showMessageDialog(this,
                        "Alle Einsätze wurden beendet.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                return;
            }

            // ===== FALL 2: Filter aktiv → nur angezeigte Einsätze löschen =====
            int sichtbareZeilen = einsatzTable.getRowCount();

            if (sichtbareZeilen == 0) {
                throw new IllegalArgumentException("Keine gefilterten Einsätze vorhanden");
            }

            // WICHTIG: von unten nach oben löschen!
            for (int i = sichtbareZeilen - 1; i >= 0; i--) {

                int modelIndex = einsatzTable.convertRowIndexToModel(i);

                einsatzListe.remove(modelIndex);
                tableModel.removeRow(modelIndex);
            }

            JOptionPane.showMessageDialog(this,
                    "Gefilterte Einsätze wurden beendet.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);

            // Filter danach zurücksetzen
            sorter.setRowFilter(null);
            filterTextField.setText("");
            filternComboBox.setSelectedItem("- Spalte auswählen");
            filterButton.setText("Filter setzen");
            einsaetzeBeendenButton.setText("Alle Einsätze beenden");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    // ==============================
    // MAIN-Methode
    // ==============================
    public static void main(String[] args) {
        new Leitstellensystem();
    }
}