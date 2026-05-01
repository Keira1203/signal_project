package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy{
    private AlertFactory factory = new BloodOxygenAlertFactory();
    @Override
    public void checkAlert(Patient patient){
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 600000,
                System.currentTimeMillis());

        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Saturation") && r.getMeasurementValue() < 92) {
                Alert alert=factory.createAlert(String.valueOf(patient.getPatientId()), "Low oxygen saturation", r.getTimestamp());
                AlertManager.getInstance().dispatchAlert(alert);
            }
        }
    }
    
}
