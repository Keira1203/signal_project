package com.alerts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private AlertManager alertManager;
    private Map<Integer, ThresholdRule> thresholdRules;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.alertManager = new AlertManager();
        this.thresholdRules = new HashMap<>();

        // an example personalized rule
        thresholdRules.put(1, new ThresholdRule("SystolicPressure", 90, 140));
        thresholdRules.put(2, new ThresholdRule("Saturation", 92, 100));
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        if (patient == null) {
            return;
        }

        checkThreshold(patient);
        checkOxygen(patient);
        checkTrend(patient);
        checkECG(patient);
        checkCombination(patient);
    }

    private void checkThreshold(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);

        for (PatientRecord record : records) {
            double val = record.getMeasurementValue();
            String type = record.getRecordType();

            if (type.equals("SystolicPressure") && (val > 180 || val < 90)) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Critical Systolic BP",
                        record.getTimestamp()));
            }

            if (type.equals("DiastolicPressure") && (val > 120 || val < 60)) {
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Critical Diastolic BP",
                        record.getTimestamp()));
            }
        }

    }

    private void checkOxygen(Patient patient) {
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 600000,
                System.currentTimeMillis());

        for (PatientRecord r : records) {
            if (r.getRecordType().equals("Saturation") && r.getMeasurementValue() < 92) {
                triggerAlert(
                        new Alert(String.valueOf(patient.getPatientId()), "Low oxygen saturation", r.getTimestamp()));
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
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Trend Alert",
                            records.get(i).getTimestamp()));
                }
            }
        }
    }

    private void checkECG(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, Long.MAX_VALUE);
        List<PatientRecord> ecgRecords = records.stream().filter(r -> r.getRecordType().equals("ECG")).toList();

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
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "ECG abnormal spike", r.getTimestamp()));
                break;
            }
        }
    }

    private void checkCombination(Patient patient) {
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

        boolean lowBP = records.stream()
                .anyMatch(r -> r.getRecordType().equals("SystolicPressure") && r.getMeasurementValue() < 90);

        boolean lowOxygen = records.stream()
                .anyMatch(r -> r.getRecordType().equals("Saturation") && r.getMeasurementValue() < 92);

        if (lowBP && lowOxygen) {
            triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Hypotensive Hypoxemia",
                    System.currentTimeMillis()));
        }
    }

    /**
     * the javadoc is too long so make it shorter
     * Triggers alert notification for critical patient condition.
     *
     * @param alert contains patient ID, condition, and severity details
     */
    private void triggerAlert(Alert alert) {
        if (alert == null) {
            return;
        }

        alertManager.dispatchAlert(alert);
    }

    /**
     * Adds or updates a threshold rule for a patient.
     *
     * @param patientId the patient ID
     * @param rule      the threshold rule to apply
     */
    public void setThresholdRule(int patientId, ThresholdRule rule) {
        thresholdRules.put(patientId, rule);
    }

    /**
     * Returns all configured threshold rules.
     *
     * @return the threshold rules map
     */
    public Map<Integer, ThresholdRule> getThresholdRules() {
        return thresholdRules;
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }

}