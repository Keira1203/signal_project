package com.alerts;

import java.util.List;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy{
    private AlertFactory factory = new BloodPressureAlertFactory();
    @Override
    public void checkAlert(Patient patient){
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        checkThresholds(patient,records);
        checkTrend(patient);
    }

    public void checkThresholds(Patient patient, List<PatientRecord> records){

        for (PatientRecord record : records) {
            double val = record.getMeasurementValue();
            String type = record.getRecordType();

            if (type.equals("SystolicPressure") && (val > 180 || val < 90)) {
                Alert alert=factory.createAlert(String.valueOf(patient.getPatientId()), "Critical Systolic BP",
                        record.getTimestamp());
                AlertManager.getInstance().dispatchAlert(alert);
            }

            if (type.equals("DiastolicPressure") && (val > 120 || val < 60)) {
                Alert alert =factory.createAlert(String.valueOf(patient.getPatientId()), "Critical Diastolic BP",
                        record.getTimestamp());
                AlertManager.getInstance().dispatchAlert(alert);
            }
        }
    }

    private void checkTrend(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        if (records.size() < 3)
            return;

        for (int i = 2; i < records.size(); i++) {
            String type1 = records.get(i - 2).getRecordType();
            String type2 = records.get(i - 1).getRecordType();
            String type3 = records.get(i).getRecordType();

            if (type1.equals(type2) && type2.equals(type3)) {
                double a = records.get(i - 2).getMeasurementValue();
                double b = records.get(i - 1).getMeasurementValue();
                double c = records.get(i).getMeasurementValue();

                boolean increasing = (b - a > 10) && (c - b > 10);
                boolean decreasing = (a - b > 10) && (b - c > 10);

                if (increasing || decreasing) {
                    Alert alert = factory.createAlert(String.valueOf(patient.getPatientId()), "Trend Alert",
                            records.get(i).getTimestamp());
                    AlertManager.getInstance().dispatchAlert(alert);
                }
            }
        }
    }
    
}
