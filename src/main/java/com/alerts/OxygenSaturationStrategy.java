package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {
  private AlertFactory factory = new BloodOxygenAlertFactory();

  @Override
  public void checkAlert(Patient patient) {
    List<PatientRecord> records =
        patient.getRecords(0, Long.MAX_VALUE); // Get all records for the patient);

    for (PatientRecord r : records) {
      if (r.getRecordType().equals("Saturation") && r.getMeasurementValue() < 92) {
        Alert alert =
            factory.createAlert(
                String.valueOf(patient.getPatientId()), "Low oxygen saturation", r.getTimestamp());
        AlertManager.getInstance().dispatchAlert(alert);
      }
    }
  }
}
