import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeitstellensystemTest {

    @Test // Test für neues Objekt in ArrayList nach Hinzufügen eines Objekts durch alarmieren()
    void alarmieren() {
        Leitstellensystem system = new Leitstellensystem();

        // Anzahl Objekte in ArrayList vor alarmieren()
        int vorher = system.einsatzListe.size();

        system.adresseTextField.setText("Teststraße");
        system.hNrTextField.setText("1");
        system.plzTextField.setText("12345");
        system.ortTextField.setText("Teststadt");
        system.bemerkungTextField.setText("Test");
        system.miGCheckBox.setSelected(true);
        system.signalfahrtCheckBox.setSelected(true);
        system.stichwortComboBox.setSelectedItem("B2 - Brandmeldeanlage");
        system.alarmieren();

        // Assert Ergebnis (ein Objekt mehr) überprüfen
        assertEquals(vorher + 1, system.einsatzListe.size());

    }
}