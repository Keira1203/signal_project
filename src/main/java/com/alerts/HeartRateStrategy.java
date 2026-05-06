package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public class HeartRateStrategy implements AlertStrategy {
  private AlertFactory factory = new ECGAlertFactory();

  @Override
  public void checkAlert(Patient patient) {
    List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
    List<PatientRecord> ecgRecords =
        records.stream().filter(r -> r.getRecordType().equals("ECG")).toList();

    if (ecgRecords.size() < 2) {
      return;
    }

    double sum = 0;
    for (PatientRecord r : ecgRecords) {
      sum += r.getMeasurementValue();
    }

    double avg = sum / ecgRecords.size();

    for (PatientRecord r : ecgRecords) {
      if (r.getMeasurementValue() > avg * 2) {
        Alert alert =
            factory.createAlert(
                String.valueOf(patient.getPatientId()), "ECG abnormal spike", r.getTimestamp());
        AlertManager.getInstance().dispatchAlert(alert);
        break;
      }
    }
  }
}
