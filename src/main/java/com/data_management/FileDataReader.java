package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {
    private String outputDirectory;

    public FileDataReader(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File folder = new File(outputDirectory);

        File[] files = folder.listFiles();

        if (files == null) {
            throw new IOException("The directory is not found or empty: " + outputDirectory);
        }

        for (File file : files) {
            if (file.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        parseAndStore(line, dataStorage);
                    }
                }
            }
        }
    }

    private void parseAndStore(String line, DataStorage dataStorage) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            try {
                int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                long timestamp = Long.parseLong(parts[1].split(":")[1].trim());
                String recordType = parts[2].split(":")[1].trim();
                double measurementValue = Double.parseDouble(parts[3].split(":")[1].trim().replace("%", ""));

                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (Exception e) {
                System.err.println("Skipping malformed line: " + line);
            }
        }
    }
}