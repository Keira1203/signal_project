package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {
  private AlertFactory factory = new BloodOxygenAlertFactory();

  @Override
  public void checkAlert(Patient patient) {
    List<PatientRecord> records =
        patient.getRecords(0, Long.MAX_VALUE); // Get all records for the patient);
    List<PatientRecord> oxygenRecords = new ArrayList<>();
    for (PatientRecord r : records) {
      if (r.getRecordType().equals("Saturation")) {
        oxygenRecords.add(r);
      }
    }

    for (int i = 0; i < oxygenRecords.size(); i++) {
      PatientRecord current = oxygenRecords.get(i);

      if (current.getMeasurementValue() < 92) {
        Alert alert =
            factory.createAlert(
                String.valueOf(patient.getPatientId()),
                "Low oxygen saturation",
                current.getTimestamp());
        AlertManager.getInstance().dispatchAlert(alert);
      }

      if (i > 0) {
        PatientRecord previous = oxygenRecords.get(i - 1);

        long timeDiff = current.getTimestamp() - previous.getTimestamp();
        double valueDiff = previous.getMeasurementValue() - current.getMeasurementValue();

        if (timeDiff < 600000 && valueDiff >= 5) {
          Alert alert =
              factory.createAlert(
                  String.valueOf(patient.getPatientId()),
                  "Rapid drop in oxygen saturation",
                  current.getTimestamp());
          AlertManager.getInstance().dispatchAlert(alert);
        }
      }
    }
  }
}
