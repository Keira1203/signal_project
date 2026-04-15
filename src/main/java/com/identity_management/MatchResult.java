package com.identity_management;

/**
 * Represents the result of trying to match simulator data to a hospital
 * patient.
 */
public class MatchResult {
    private boolean matched;
    private boolean anomalyDetected;
    private String message;
    private HospitalPatient patient;

    public MatchResult(boolean matched, boolean anomalyDetected, String message, HospitalPatient patient) {
        this.matched = matched;
        this.anomalyDetected = anomalyDetected;
        this.message = message;
        this.patient = patient;
    }

    public boolean isMatched() {
        return matched;
    }

    public boolean isAnomalyDetected() {
        return anomalyDetected;
    }

    public String getMessage() {
        return message;
    }

    public HospitalPatient getPatient() {
        return patient;
    }

}
