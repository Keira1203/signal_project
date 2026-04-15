package com.identity_management;

/**
 * Represents a patient record stored in the hospital system.
 */
public class HospitalPatient {
    private int hospitalPatientId;
    private int simulatedPatientId;
    private String name;
    private String medicalHistory;

    public HospitalPatient(int hospitalPatientId, int simulatedPatientId, String name, String medicalHistory) {
        this.hospitalPatientId = hospitalPatientId;
        this.simulatedPatientId = simulatedPatientId;
        this.name = name;
        this.medicalHistory = medicalHistory;
    }

    public int getHospitalPatientId() {
        return hospitalPatientId;
    }

    public int getSimulatedPatientId() {
        return simulatedPatientId;
    }

    public String getName() {
        return name;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

}
