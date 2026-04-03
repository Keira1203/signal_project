package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * generate the abnormal alert from possibility thinking
 * 
 * calculate the possibility with poisson and when once happen ,
 * solve it with 90% of possibility.
 * let remain continuous
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed

    /**
     * initialize all patients alert situation list
     * 
     * @param patientCount the number of patient
     *                     start with all false(non alert)
     */
    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

    /**
     * update the certain patient alert situation and output
     * 
     * if no alert lambda=0.1 and check new alert happen or no
     * if alert exists, solve with 90% possibility automatically
     * output with "Alert", "triggered", "resolved"
     * 
     * @param patientId      the certain patient
     * @param outputStrategy place to output
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
