package com.main;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.Patient;
import com.alerts.AlertGenerator;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equalsIgnoreCase("simulator")) {
            HealthDataSimulator.main(args);
        } else {
            parseAndAnalyze();
        }
    }

    private static void parseAndAnalyze() throws IOException {
        DataStorage storage = new DataStorage();
        FileDataReader reader = new FileDataReader("output");
        AlertGenerator alertGenerator = new AlertGenerator(storage);

        reader.readData(storage);

        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }
}