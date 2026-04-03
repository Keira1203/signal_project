package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The common class rule to generate patients health data
 * make all health data per patient and send with certain method
 */
public interface PatientDataGenerator {
    /**
     * generates the patients data and send it
     * 
     * @param patientId      patients ID of the data generating patient
     * @param outputStrategy set where to send the data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
