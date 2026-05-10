package com.data_management;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient extends WebSocketClient {
  private DataStorage storage;

  public MyWebSocketClient(URI serverUri, DataStorage storage) {
    super(serverUri);
    this.storage = storage;
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    System.out.println("Connected to WebSocket server: " + getURI());
  }

  @Override
  public void onMessage(String message) {
    // Incoming data messages are formatted correctly and parsed
    try {
      parseAndStoreMessage(message);
    } catch (Exception e) {
      System.err.println("Error parsing message: " + message);
    }
  }

  private void parseAndStoreMessage(String message) {
    System.out.println("RAW MESSAGE: " + message);
    String[] parts = message.split(",");
    if (parts.length == 4) {
      try {
        int patientId = Integer.parseInt(parts[0].trim());
        long timestamp = Long.parseLong(parts[1].trim());
        String recordType = parts[2].trim();

        // delate the "label" part and parse the measurement value directly
        String valueStr = parts[3].trim().replace("%", "");
        double measurementValue = Double.parseDouble(valueStr);
        storage.addPatientData(patientId, measurementValue, recordType, timestamp);
        System.out.println(
            "Saved data: Patient "
                + patientId
                + " ("
                + recordType
                + ") Value: "
                + measurementValue);
      } catch (Exception e) {
        System.err.println("Skipping malformed message: " + message);
      }
    } else {
      System.err.println("Invalid message format: " + message);
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    // Interruptions in the data stream
    System.out.println("WebSocket connection closed: " + reason);
  }

  @Override
  public void onError(Exception ex) {
    // Errors in the WebSocket connection
    System.err.println("WebSocket error: " + ex.getMessage());
  }
}
