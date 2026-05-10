package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataStorageTest {

  @BeforeEach
  void setUp() {
    DataStorage.getInstance().reset(); // Clear storage before each test
  }

  @Test
  void testAddAndGetRecords() {
    // DataReader reader
    DataStorage storage = DataStorage.getInstance();
    storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
    storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

    List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
    assertEquals(2, records.size()); // Check if two records are retrieved
    assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
  }

  @Test
  void testGetRecordsEmpty() {
    DataStorage storage = DataStorage.getInstance();
    List<PatientRecord> records = storage.getRecords(999, 0, 10000);
    assertTrue(records.isEmpty());
  }

  @Test
  void testMultiplePatient() {
    DataStorage storage = DataStorage.getInstance();
    storage.addPatientData(1, 100, "HeartRate", 1000);
    storage.addPatientData(2, 200, "HeartRate", 1000);
    assertEquals(1, storage.getRecords(1, 0, 2000).size());
    assertEquals(1, storage.getRecords(2, 0, 2000).size());
  }

  @Test
  void testTimeFiltering() {
    DataStorage storage = DataStorage.getInstance();
    storage.addPatientData(1, 100, "HeartRate", 1000);
    storage.addPatientData(1, 200, "HeartRate", 5000);
    List<PatientRecord> records = storage.getRecords(1, 0, 2000);
    assertEquals(1, records.size());
  }
}
