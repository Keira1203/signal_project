package com.data_management;

/**
 * Controls access to patient data based on user role.
 */
public class AccessControl {

    public boolean canAccess(String userRole, int patientId) {
        if (userRole == null) {
            return false;
        }

        return userRole.equalsIgnoreCase("Doctor")
                || userRole.equalsIgnoreCase("Nurse")
                || userRole.equalsIgnoreCase("Admin");
    }
}