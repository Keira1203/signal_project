package com.alerts;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles routing and dispatching alerts to medical staff.
 */
public class AlertManager {

    private List<Alert> alerts = new ArrayList<>();

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
        System.out.println(
                "Dispatching alert for patient " + alert.getPatientId()
                        + " | condition: " + alert.getCondition()
                        + " | timestamp: " + alert.getTimestamp());
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

}