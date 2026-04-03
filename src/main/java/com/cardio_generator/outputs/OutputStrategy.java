package com.cardio_generator.outputs;

/**
 * the common rule to output the patients data
 * to output in the console, save to file, send TCP,
 */
public interface OutputStrategy {
    /**
     * send the patients data to certain place
     * 
     * @param patientId which patient
     * @param timestamp when the data generated
     * @param label     the kind of data
     * @param data      the actual data
     */
    void output(int patientId, long timestamp, String label, String data);
}
