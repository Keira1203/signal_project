package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.MyWebSocketClient;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyWebSocketClientTest {
  private DataStorage storage;
  private MyWebSocketClient client;

  @BeforeEach
  void setUp() {
    storage = DataStorage.getInstance();
    storage.reset(); // Clear storage before each test
    client = new MyWebSocketClient(URI.create("ws://localhost:8080"), storage);
  }

  @Test
  void testParseValidSaturation() {
    client.onMessage("1, 1000, Saturation, 95.0%");

    var records = storage.getRecords(1, 999, 1001);
    assertEquals(1, records.size());
    assertEquals(95.0, records.get(0).getMeasurementValue());
    assertEquals("Saturation", records.get(0).getRecordType());
  }

  @Test
  void testParseValidECG() {
    client.onMessage("2, 2000, ECG, 0.123");

    var records = storage.getRecords(2, 1999, 2001);
    assertEquals(1, records.size());
    assertEquals(0.123, records.get(0).getMeasurementValue());
  }

  @Test
  void testInvalidFormatDoesNotCrash() {
    assertDoesNotThrow(
        () -> {
          client.onMessage("invalid message");
          client.onMessage("1, 2, 3"); // Missing value part
        });

    assertTrue(storage.getRecords(1, 0, Long.MAX_VALUE).isEmpty());
  }

  @Test
  void testNonNumericValue() {
    assertDoesNotThrow(
        () -> {
          client.onMessage("1, 1000, Saturation, NaN%");
        });
  }
}
