package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data and generating alerts
 * when certain predefined conditions are met. This class relies on a {@link DataStorage} instance
 * to access patient data and evaluate it against specific health criteria.
 */
public class AlertGenerator {
  private AlertManager alertManager;
  private Map<Integer, ThresholdRule> thresholdRules;
  private List<AlertStrategy> strategies;

  /**
   * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}. The {@code
   * DataStorage} is used to retrieve patient data that this class will monitor and evaluate.
   *
   * @param dataStorage the data storage system that provides access to patient data
   */
  public AlertGenerator(DataStorage dataStorage) {
    this.alertManager = AlertManager.getInstance();
    this.thresholdRules = new HashMap<>();
    this.strategies = new ArrayList<>();

    strategies.add(new BloodPressureStrategy());
    strategies.add(new OxygenSaturationStrategy());
    strategies.add(new HeartRateStrategy());

    // an example personalized rule
    thresholdRules.put(1, new ThresholdRule("SystolicPressure", 90, 140));
    thresholdRules.put(2, new ThresholdRule("Saturation", 92, 100));
  }

  /**
   * Evaluates the specified patient's data to determine if any alert conditions are met. If a
   * condition is met, an alert is triggered via the {@link #triggerAlert} method. This method
   * should define the specific conditions under which an alert will be triggered.
   *
   * @param patient the patient data to evaluate for alert conditions
   */
  public void evaluateData(Patient patient) {
    if (patient == null) {
      return;
    }

    for (AlertStrategy strategy : strategies) {
      strategy.checkAlert(patient);
    }
    checkCombination(patient);
  }

  private void checkCombination(Patient patient) {
    List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

    boolean lowBP =
        records.stream()
            .anyMatch(
                r -> r.getRecordType().equals("SystolicPressure") && r.getMeasurementValue() < 90);

    boolean lowOxygen =
        records.stream()
            .anyMatch(r -> r.getRecordType().equals("Saturation") && r.getMeasurementValue() < 92);

    if (lowBP && lowOxygen) {
      triggerAlert(
          new BasicAlert(
              String.valueOf(patient.getPatientId()),
              "Hypotensive Hypoxemia",
              System.currentTimeMillis()));
    }
  }

  /**
   * the javadoc is too long so make it shorter Triggers alert notification for critical patient
   * condition.
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
   * @param rule the threshold rule to apply
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
