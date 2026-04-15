package com.data_access;

/**
 * Parses raw incoming data into a standard internal format.
 */
public class DataParser {

    public ParsedData parse(String rawData) {
        String[] parts = rawData.split(",");

        int patientId = Integer.parseInt(parts[0]);
        String measurementType = parts[1];
        double measurementValue = Double.parseDouble(parts[2]);
        long timestamp = Long.parseLong(parts[3]);

        return new ParsedData(patientId, measurementType, measurementValue, timestamp);
    }
}