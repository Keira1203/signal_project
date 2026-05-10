package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.MyWebSocketClient;
import com.data_management.Patient;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationTest {
  private DataStorage storage;
  private AlertGenerator alertGenerator;
  private MyWebSocketClient client;

  @BeforeEach
  void setUp() {
    // reset all data and create new instances for each test
    storage = DataStorage.getInstance();
    storage.reset();
    alertGenerator = new AlertGenerator(storage);
    // generate a dummy websocket client for testing, it won't actually connect to a server in this
    // test
    client = new MyWebSocketClient(URI.create("ws://localhost:8080"), storage);
  }

  @Test
  void testFullFlow_FromWebSocketToAlert() {
    // insert data
    client.onMessage("1, 1714376789000, Saturation, 85.0%");

    Patient testPatient = new Patient(1);
    testPatient.addRecord(85.0, "Saturation", 1714376789000L);

    alertGenerator.evaluateData(testPatient);

    var alerts = alertGenerator.getAlertManager().getAlerts();
    System.out.println("Generated alerts count:" + alerts.size());
    for (Alert a : alerts) {
      System.out.println(
          "Alert for patient " + a.getPatientId() + " with condition: " + a.getCondition());
    }
    assertFalse(alerts.isEmpty(), "An alert should be generated");
  }
}
