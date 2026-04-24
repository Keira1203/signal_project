package data_management;

import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.alerts.AlertGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class AlertGeneratorTest {

    @Test
    void testNoThresholdRule() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(999);
        generator.evaluateData(patient);

        assertEquals(0, generator.getAlertManager().getAlerts().size());
    }

    @Test
    void testThresholdBreached() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(1);
        patient.addRecord(200, "SystolicPressure", System.currentTimeMillis());

        generator.evaluateData(patient);

        // check wether it does not throws an error
        assertEquals(1, generator.getAlertManager().getAlerts().size());
    }

    @Test
    void testDoesNotCrushWithValidInput() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);

        Patient patient = new Patient(2);
        patient.addRecord(120, "SystolicPressure", System.currentTimeMillis());

        assertDoesNotThrow(() -> generator.evaluateData(patient));
    }

    @Test
    void testThresholdAlert() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);

        Patient patient = new Patient(1);
        patient.addRecord(50, "SystolicPressure", System.currentTimeMillis());

        generator.evaluateData(patient);

        assertEquals(1, generator.getAlertManager().getAlerts().size());
    }

    @Test
    void testLowOxygen() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);

        Patient patient = new Patient(1);
        patient.addRecord(80, "Saturation", System.currentTimeMillis());

        generator.evaluateData(patient);

        assertEquals(1, generator.getAlertManager().getAlerts().size());
    }

    @Test
    void testTrendAlert() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(1);
        long t = System.currentTimeMillis();

        patient.addRecord(100, "SystolicPressure", t);
        patient.addRecord(120, "SystolicPressure", t + 1000);
        patient.addRecord(145, "SystolicPressure", t + 2000);

        generator.evaluateData(patient);

        assertTrue(generator.getAlertManager().getAlerts().stream().anyMatch(a -> a.getCondition().contains("Trend")));

    }

    @Test
    void testECGAlert() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(1);
        long t = System.currentTimeMillis();
        patient.addRecord(10, "ECG", t);
        patient.addRecord(10, "ECG", t + 1000);
        patient.addRecord(10, "ECG", t + 2000);
        patient.addRecord(100, "ECG", t + 3000);
        generator.evaluateData(patient);

        assertTrue(generator.getAlertManager().getAlerts().stream().anyMatch(a -> a.getCondition().contains("ECG")));
    }

    @Test
    void testCombinationAlert() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);

        Patient patient = new Patient(1);
        patient.addRecord(80, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(80, "Saturation", System.currentTimeMillis());

        generator.evaluateData(patient);

        assertTrue(generator.getAlertManager().getAlerts().stream()
                .anyMatch(a -> a.getCondition().contains("Hypotensive Hypoxemia")));
    }

}
