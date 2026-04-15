package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles retrieval requests from authorized medical staff.
 */
public class DataRetriever {

    private DataStorage dataStorage;
    private AccessControl accessControl;

    public DataRetriever(DataStorage dataStorage, AccessControl accessControl) {
        this.dataStorage = dataStorage;
        this.accessControl = accessControl;
    }

    public List<PatientRecord> retrievePatientData(String userRole, int patientId, long startTime, long endTime) {
        if (!accessControl.canAccess(userRole, patientId)) {
            return new ArrayList<>();
        }

        return dataStorage.getRecords(patientId, startTime, endTime);
    }
}