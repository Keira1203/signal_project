package com.main;

import com.alerts.AlertGenerator;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataReader;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.WebSocketDataReader;
import java.io.IOException;

public class Main {
  /**
   * public static void main(String[] args) throws IOException { if (args.length > 0 &&
   * args[0].equalsIgnoreCase("simulator")) { HealthDataSimulator.main(args); } else {
   * parseAndAnalyze(); } }
   */
  public static void main(String[] args) throws IOException {
    String[] simulatorArgs = new String[] {"--patient-count", "10", "--output", "websocket:8887"};

    Thread simulatorThread =
        new Thread(
            () -> {
              try {
                HealthDataSimulator.main(simulatorArgs);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
    simulatorThread.start();

    try {
      // Wait for the simulator to start and produce some data
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    parseAndAnalyze();
  }

  private static void parseAndAnalyze() throws IOException {
    DataStorage storage = DataStorage.getInstance();
    DataReader reader = new WebSocketDataReader("ws://localhost:8887");
    AlertGenerator alertGenerator = new AlertGenerator(storage);

    reader.readData(storage);

    while (true) {
      try {
        Thread.sleep(5000);
        for (Patient patient : storage.getAllPatients()) {
          alertGenerator.evaluateData(patient);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
  }
}
