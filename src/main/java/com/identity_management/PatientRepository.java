package com.identity_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and retrieves hospital patient records
 */
public class PatientRepository {
    private List<HospitalPatient> patients;

    public PatientRepository() {
        this.patients = new ArrayList<>();

        // Just an example
        patients.add(new HospitalPatient(1001, 1, "Alice Brown", "Hypertension"));
        patients.add(new HospitalPatient(1002, 2, "David Smith", "Arrhythmia"));
    }

    public HospitalPatient findBySimulatedId(int simulatedPatientId) {
        for (HospitalPatient patient : patients) {
            if (patient.getSimulatedPatientId() == simulatedPatientId) {
                return patient;
            }
        }

        return null;

    }

    public List<HospitalPatient> findAll() {
        return patients;
    }

}
