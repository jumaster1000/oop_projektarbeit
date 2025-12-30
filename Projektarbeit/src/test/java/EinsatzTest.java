import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EinsatzTest {
    // Test für vollständige Adresse
    @Test
    void Volladresse() {
        Einsatz e = new Einsatz(
                "Münchner Straße",
                "12",
                89073,
                "Ulm",
                "Rauchentwicklung",
                false,
                "B2 – Rauchentwicklung",
                true
        );

        // Act (Methode ausführen)
        String adresse = e.Volladresse();

        // Assert (Ergebnis überprüfen)
        assertEquals("Münchner Straße 12, 89073 Ulm", adresse);
    }

    // Test für dringenden & nicht dringenden Einsatz
    @Test
    void istDringend() {
        // --- FALL 1: dringender Einsatz
        Einsatz e1 = new Einsatz(
                "Münchner Straße",
                "12",
                89073,
                "Ulm",
                "Rauchentwicklung",
                true,   // MiG
                "B2 – Rauchentwicklung",
                true    // Signalfahrt
        );

        // Assert (Ergebnis überprüfen)
        assertTrue(e1.istDringend());

        // --- FALL 2: nicht dringender Einsatz
        Einsatz e2 = new Einsatz(
                "Münchner Straße",
                "12",
                89073,
                "Ulm",
                "Rauchentwicklung",
                false,   // MiG
                "B2 – Rauchentwicklung",
                true // Signalfahrt
        );

        // Assert (Ergebnis überprüfen)
        assertFalse(e2.istDringend());
    }
}