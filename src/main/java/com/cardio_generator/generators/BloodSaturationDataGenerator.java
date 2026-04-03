package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * generate simulations of all patients blood saturation
 * 
 * move the number from the previous data and revival the natural transition
 * memorize the value of each patient and make it continuous
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;

    /**
     * set all patients blood saturation data and prepare
     * 
     * @param patientCount the number of patient
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * make the blood saturation level and send it to output
     * 
     * @param patientId      the certain patient
     * @param outputStrategy place to output
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
