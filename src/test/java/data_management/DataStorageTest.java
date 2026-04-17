package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.Scanner;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        // TODO: A mock data reader could be added in a future test setup.
        // DataReader reader
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testGetRecordsEmpty() {
        DataStorage storage = new DataStorage();
        List<PatientRecord> records = storage.getRecords(999, 0, 10000);
        assertTrue(records.isEmpty());
    }

    @Test
    void testMultiplePatient() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100, "HeartRate", 1000);
        storage.addPatientData(2, 200, "HeartRate", 1000);
        assertEquals(1, storage.getRecords(1, 0, 2000).size());
        assertEquals(1, storage.getRecords(2, 0, 2000).size());
    }

    @Test
    void testTimeFiltering() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100, "HeartRate", 1000);
        storage.addPatientData(1, 200, "HeartRate", 5000);
        List<PatientRecord> records = storage.getRecords(1, 0, 2000);
        assertEquals(1, records.size());
    }

}
