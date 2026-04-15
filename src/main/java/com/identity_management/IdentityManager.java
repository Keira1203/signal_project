package com.identity_management;

/**
 * Coordinates patient identification and handles mismatches.
 */
public class IdentityManager {
    private PatientIdentifier patientIdentifier;

    public IdentityManager(PatientIdentifier patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public MatchResult processIdentification(int simulatedPatientId) {
        MatchResult result = patientIdentifier.matchPatient(simulatedPatientId);

        if (!result.isMatched()) {
            handleMismatch(simulatedPatientId);
        }

        return result;

    }

    public void handleMismatch(int simulatedPatientId) {
        System.out.println("Unmatched simulator patient ID= " + simulatedPatientId);
    }
}
