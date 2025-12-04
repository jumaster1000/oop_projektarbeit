import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Leitstellensystem extends JFrame {
    private JPanel hauptPanel;
    private JLabel einsatzAnlegenLabel;
    private JLabel adresseLabel;
    private JTextField adresseTextField;
    private JLabel hNrLabel;
    private JLabel postleitzahlLabel;
    private JTextField plzTextField;
    private JLabel ortLabel;
    private JTextField ortTextField;
    private JLabel bemerkungLabel;
    private JTextField hNrTextField;
    private JLabel MiGLabel;
    private JCheckBox miGCheckBox;
    private JLabel stichwortLabel;
    private JComboBox stichwortComboBox;
    private JLabel signalfahrtLabel;
    private JCheckBox signalfahrtCheckBox;
    private JButton alarmierenButton;
    private JLabel leitstelleIcon;
    private JButton eingabeLoeschen;
    private JTextArea bemerkungTextArea;

    public Leitstellensystem(){

        setLocationRelativeTo(null);
        setTitle("Leitstellensystem - Leitstelle Ulm / Neu-Ulm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(hauptPanel);
        setSize(700,400);
        pack();
        setVisible(true);
        setResizable(false);
        alarmierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmieren();
            }
        });
        eingabeLoeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adresseTextField.setText("");
                hNrTextField.setText("");
                plzTextField.setText("");
                ortTextField.setText("");
                bemerkungTextArea.setText("- hier Bemerkung einfügen -");
                miGCheckBox.setSelected(false);
                stichwortComboBox.setSelectedItem("- Stichwort auswählen -");
                signalfahrtCheckBox.setSelected(false);            }
        });
    }

    public void alarmieren(){
        String adresse = adresseTextField.getText();
        String hausNr = hNrTextField.getText();
        int plz = Integer.parseInt(plzTextField.getText());
        String ort = ortTextField.getText();
        String bemerkung = bemerkungTextArea.getText();
        boolean miG;
        if (miGCheckBox.isSelected()) {
            miG = true;
        } else miG = false;
        String stichwort = stichwortComboBox.getSelectedItem().toString();
        boolean signalfahrt;
        if (miGCheckBox.isSelected()) {
            signalfahrt = true;
        } else signalfahrt = false;

    }

    public static void main(String[] args) {
        new Leitstellensystem();
    }
}
