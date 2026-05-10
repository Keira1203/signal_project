package com.alerts;

import java.util.ArrayList;
import java.util.List;

/** Handles routing and dispatching alerts to medical staff. */
public class AlertManager {

  private List<Alert> alerts = new ArrayList<>();
  private static AlertManager instance;

  private AlertManager() {}

  public static synchronized AlertManager getInstance() {
    if (instance == null) {
      instance = new AlertManager();
    }
    return instance;
  }

  /**
   * Dispatches an alert to the appropriate destination.
   *
   * @param alert the alert to send
   */
  public void dispatchAlert(Alert alert) {
    if (alert == null) {
      return;
    }

    alerts.add(alert);
    notifyStaff(alert);
  }

  /**
   * Simulates notifying medical staff about an alert.
   *
   * @param alert the alert being sent
   */
  public void notifyStaff(Alert alert) {
    String style = "\u001B[41;37m"; // Red background with white text
    String condition = alert.getCondition();
    if (condition.contains("ECG")) {
      style = "\u001B[43;30m"; // Yellow background with black text
    } else if (condition.contains("Oxygen")) {
      style = "\u001B[44;37m"; // Blue background with white text
    } else if (condition.contains("Blood Pressure")) {
      style = "\u001B[45;37m"; // Magenta background with white text
    }
    String reset = "\u001B[0m"; // Reset to default
    System.out.println(
        style
            + "ALERT: Patient "
            + alert.getPatientId()
            + " has condition: "
            + alert.getCondition()
            + " at "
            + alert.getTimestamp()
            + reset);
  }

  public List<Alert> getAlerts() {
    return alerts;
  }

  public void clearAlerts() {
    this.alerts.clear();
  }
}
