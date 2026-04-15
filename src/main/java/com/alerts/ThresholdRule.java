package com.alerts;

/**
 * Represents a threshold rule for a patient's vital sign.
 */
public class ThresholdRule {

    private String metricType;
    private double minValue;
    private double maxValue;

    /**
     * Creates a threshold rule for a given metric type.
     *
     * @param metricType the metric name, e.g. HeartRate, SystolicPressure
     * @param minValue   minimum allowed value
     * @param maxValue   maximum allowed value
     */
    public ThresholdRule(String metricType, double minValue, double maxValue) {
        this.metricType = metricType;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Checks whether a value violates this threshold rule.
     *
     * @param value the measured value
     * @return true if the value is outside the allowed range
     */
    public boolean isBreached(double value) {
        return value < minValue || value > maxValue;
    }

    public String getMetricType() {
        return metricType;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}