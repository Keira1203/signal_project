package com.data_management;

/**
 * Defines how long patient data should be retained.
 */
public class RetentionPolicy {

    private int retentionDays;

    public RetentionPolicy(int retentionDays) {
        this.retentionDays = retentionDays;
    }

    public boolean isExpired(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long retentionMillis = (long) retentionDays * 24 * 60 * 60 * 1000;
        return currentTime - timestamp > retentionMillis;
    }

    public int getRetentionDays() {
        return retentionDays;
    }
}