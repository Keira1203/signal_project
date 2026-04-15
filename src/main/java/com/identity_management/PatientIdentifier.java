package com.identity_management;

/**
 * Matches incoming simulator IDs to hospital patient records.
 */
public class PatientIdentifier {

    private PatientRepository patientRepository;

    public PatientIdentifier(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public MatchResult matchPatient(int simulatedPatientId) {
        HospitalPatient patient = patientRepository.findBySimulatedId(simulatedPatientId);

        if (patient == null) {
            return new MatchResult(false, true, "No matching patient", null);
        }

        return new MatchResult(true, false, "Patient Matched!", patient);

    }

}
