package com.alerts;

/**
 * Handles routing and dispatching alerts to medical staff.
 */
public class AlertManager {

    /**
     * Dispatches an alert to the appropriate destination.
     *
     * @param alert the alert to send
     */
    public void dispatchAlert(Alert alert) {
        if (alert == null) {
            return;
        }

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
}