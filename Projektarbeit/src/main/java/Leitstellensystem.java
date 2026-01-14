import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;


public class Leitstellensystem extends JFrame {
    // ------------------------------
    // Attribute Initialisieren
    // ------------------------------
    // Haupt Panel
    private JPanel hauptPanel;


    // Adresse
    public JTextField adresseTextField;     // eigentlich private, aber für JUnit Test Zugriff public
    public JTextField hNrTextField;         // eigentlich private, aber für JUnit Test Zugriff public
    public JTextField plzTextField;         // eigentlich private, aber für JUnit Test Zugriff public
    public JTextField ortTextField;         // eigentlich private, aber für JUnit Test Zugriff public

    // Zusatzinformationen
    public JTextField bemerkungTextField;   // eigentlich private, aber für JUnit Test Zugriff public
    public JCheckBox miGCheckBox;           // eigentlich private, aber für JUnit Test Zugriff public
    public JComboBox stichwortComboBox;     // eigentlich private, aber für JUnit Test Zugriff public
    public JCheckBox signalfahrtCheckBox;   // eigentlich private, aber für JUnit Test Zugriff public

    // Buttons
    private JButton alarmierenButton;
    private JButton eingabeLoeschen;

    // Einsatzverwaltung
    private JTable einsatzTable;
    private DefaultTableModel tableModel;
    public ArrayList<Einsatz> einsatzListe = new ArrayList<>(); // eigentlich private, aber für JUnit Test Zugriff public

    // Filter
    private JTextField filterTextField;
    private JButton filterButton;
    private JComboBox filternComboBox;
    private JButton einsaetzeBeendenButton;
    private JButton alphabetischSortierenButton;
    private TableRowSorter<DefaultTableModel> sorter;

    // ------------------------------
    // Konstruktor
    // ------------------------------
    public Leitstellensystem(){
        // Demo-Einsätze erstellen
        initObjekte();

        // Spalten der Tabelle definieren
        String[] columnNames = {
                "Stichwort", "Adresse", "Bemerkung", "Ort", "MiG", "Signalfahrt"
        };

        // TableModel aufbauen
        tableModel = new DefaultTableModel(columnNames, 0);

        // Das Model an deine JTable aus dem Designer geben & Zeilensortierer initialisieren
        einsatzTable.setModel(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        einsatzTable.setRowSorter(sorter);
        einsatzTable.setDefaultEditor(Object.class, null);

        // Spaltenbreiten der Tabelle anpassen
        einsatzTable.getColumnModel().getColumn(0).setPreferredWidth(150);  // Stichwort
        einsatzTable.getColumnModel().getColumn(1).setPreferredWidth(200);  // Adresse
        einsatzTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Bemerkung
        einsatzTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Ort
        einsatzTable.getColumnModel().getColumn(4).setPreferredWidth(15);   // MiG
        einsatzTable.getColumnModel().getColumn(5).setPreferredWidth(30);   // Signalfahrt

        // Demo-Einsätze ausgeben
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

        // Rest des Konstruktors / Definition GUI-Fenster
        setTitle("Leitstellensystem - Leitstelle Ulm / Neu-Ulm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(hauptPanel);
        setSize(1050,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        // ==============================
        // Action Listener
        // ==============================
        alarmierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmieren();
            }
        });
        eingabeLoeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eingabeLoeschen();
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtern();
            }
        });
        einsaetzeBeendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                einsaetzeBeenden();
            }
        });
        alphabetischSortierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alphabetischSortieren();
            }
        });
    }

    // ------------------------------
    // Demo-Einsätze
    // - Drei Einsätze werden als Demo in der Tabelle bei Start des Programmes ausgegeben
    // ------------------------------
    public void initObjekte(){
        einsatzListe.add(new Einsatz(
                "Münchner Straße", "12", 89073, "Ulm",
                "Nachbar sieht Rauch", false,
                "B2 – Rauchentwicklung", true
        ));

        einsatzListe.add(new Einsatz(
                "Karlstraße", "5", 89231, "Neu-Ulm",
                "VU mit PKW", true,
                "H2 – VU PKW (Person eingekl.)", true
        ));

        einsatzListe.add(new Einsatz(
                "Wileystraße", "1", 89231, "Neu-Ulm",
                "Hauptmeldernummer 148; HochschuleNU", false,
                "B2 - Brandmeldeanlage", true
        ));
    }

    // ------------------------------
    // Button: Eingabe Löschen
    // - Die Eingabefelder werden zurückgesetzt
    // ------------------------------
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


    // ------------------------------
    // Button: Alarmieren
    // - Die eingegebenen Informationen werden in ein Objekt gespeichert. Das neu erstellte Objekt wird
    // in der Tabelle ausgegeben
    // ------------------------------
    public void alarmieren(){
        try {
            // Daten aus dem Formular abspeichern
            String adresse = adresseTextField.getText();
            String hausNr = hNrTextField.getText();
            String ort = ortTextField.getText();

            // Pflichtfelder prüfen
            if (adresse.isEmpty() || hausNr.isEmpty() || ort.isEmpty() || plzTextField.getText().isEmpty()) {
                throw new IllegalArgumentException("Bitte alle Felder ausfüllen");
            }

            // PLZ Prüfung
            for (char c : plzTextField.getText().toCharArray()) {
                if (!Character.isDigit(c)) {
                    throw new IllegalArgumentException("PLZ muss eine Zahl sein");
                }
            }

            if (plzTextField.getText().length() != 5) {
                throw new IllegalArgumentException("PLZ muss 5-stellig sein");
            }

            int plz = Integer.parseInt(plzTextField.getText());

            // Weitere Felder prüfen
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

            // Objekt erstellen - siehe Klasse 'Einsatz'
            Einsatz einsatz = new Einsatz(adresse, hausNr, plz, ort, bemerkung, miG, stichwort, signalfahrt);

            // Objekt in Array Liste speichern
            einsatzListe.add(einsatz);

            // In Tabelle einfügen
            tableModel.addRow(new Object[]{
                    einsatz.getStichwort(),
                    einsatz.getAdresse() + " " + einsatz.getHausNr(),
                    einsatz.getBemerkung(),
                    einsatz.getOrt() + " (" + einsatz.getPlz() + ")",
                    einsatz.getMiG() ? "Ja" : "Nein", // MHvKI
                    einsatz.getSignalfahrt() ? "Ja" : "Nein" // MHvKI
            });

            JOptionPane.showMessageDialog(this,
                    "Einsatzkräfte wurden alarmiert",
                    "Alarmierung erfolgreich",
                    JOptionPane.INFORMATION_MESSAGE);

            // Eingabefelder zurücksetzen
            eingabeLoeschen();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------
    // Einsätze filtern
    // - Nach einer Auswahl einer Spalte und Eingabe eines Suchbegriffs wird die Tabelle gefiltert
    // ------------------------------
    public void filtern() {
        try {
            // --- FALL 1: Filter ist aktiv → Filter löschen
            if (filterButton.getText().equals("Filter löschen")) {
                sorter.setRowFilter(null); // Filter entfernen
                filternComboBox.setSelectedItem("- Spalte auswählen");
                filterTextField.setText("");
                filterButton.setText("Filter setzen");
                einsaetzeBeendenButton.setText("Alle Einsätze beenden");
                return;
            }

            // --- FALL 2: Filter setzen
            String spalte = filternComboBox.getSelectedItem().toString();
            String suchbegriff = filterTextField.getText();

            // Eingaben prüfen
            if (spalte.equals("- Spalte auswählen")) {
                throw new IllegalArgumentException("Bitte Spalte auswählen");
            }

            if (suchbegriff.equals("")) {
                throw new IllegalArgumentException("Bitte Suchbegriff eingeben");
            }

            // Spaltenindex speichern
            int columnIndex;

            switch (spalte) {
                case "Stichwort":
                    columnIndex = 0;
                    break;
                case "Adresse":
                    columnIndex = 1;
                    break;
                case "Bemerkung":
                    columnIndex = 2;
                    break;
                case "Ort":
                    columnIndex = 3;
                    break;
                case "MiG":
                    columnIndex = 4;
                    break;
                case "Signalfahrt":
                    columnIndex = 5;
                    break;
                default:
                    throw new IllegalArgumentException("Ungültige Spalte");
            }

            // Filter anwenden
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + suchbegriff, columnIndex)); // MHvKI

            // Button-Zustände aktualisieren
            filterButton.setText("Filter löschen");
            einsaetzeBeendenButton.setText("Ausgewählte Einsätze beenden");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------
    // Einsätze beenden
    // - Wenn kein Filter gesetzt sind, wird die EinsatzListe komplett gelöscht, bei einem gesetzten Filter werden
    // nur die gefilterten/angezeigten Einsätze aus der EinsatzListe gelöscht
    // ------------------------------
    public void einsaetzeBeenden() {
        try {

            // FALL 1: Kein Filter aktiv → alle Einsätze löschen
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

            }

            // FALL 2: Filter aktiv → nur angezeigte Einsätze löschen
            int sichtbareZeilen = einsatzTable.getRowCount();

            if (sichtbareZeilen == 0) {
                throw new IllegalArgumentException("Keine gefilterten Einsätze vorhanden");
            }

            // Zeilen von unten nach oben löschen
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
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ------------------------------
    // Sortieren
    // - Nach Auswahl von einer Spalte wird die Tabelle alphabetisch sortiert
    // ------------------------------
    public void alphabetischSortieren(){
        try {
            String spalte = filternComboBox.getSelectedItem().toString();

            if (spalte.equals("- Spalte auswählen")) {
                throw new IllegalArgumentException("Bitte Spalte zum Sortieren auswählen");
            }

            // Spaltenindex speichern
            int columnIndex;
            switch (spalte) {
                case "Stichwort":
                    columnIndex = 0;
                    break;
                case "Adresse":
                    columnIndex = 1;
                    break;
                case "Bemerkung":
                    columnIndex = 2;
                    break;
                case "Ort":
                    columnIndex = 3;
                    break;
                case "MiG":
                    columnIndex = 4;
                    break;
                case "Signalfahrt":
                    columnIndex = 5;
                    break;
                default:
                    throw new IllegalArgumentException("Ungültige Spalte");
            }

            sorter.setSortKeys(List.of(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING))); // MHvKI

            sorter.sort(); // MHvKI

            filternComboBox.setSelectedItem("- Spalte auswählen");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------
    // MAIN-Methode
    // ------------------------------
    public static void main(String[] args) {
        new Leitstellensystem();
    }
}