package com.data_access;

/**
 * Represents standardized parsed patient data.
 */
public class ParsedData {

    private int patientId;
    private String measurementType;
    private double measurementValue;
    private long timestamp;

    public ParsedData(int patientId, String measurementType, double measurementValue, long timestamp) {
        this.patientId = patientId;
        this.measurementType = measurementType;
        this.measurementValue = measurementValue;
        this.timestamp = timestamp;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public long getTimestamp() {
        return timestamp;
    }
}