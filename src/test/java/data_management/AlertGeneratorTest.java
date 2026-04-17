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

}
