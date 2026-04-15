package com.data_access;

/**
 * Reads patient data from a file-based source.
 */
public class FileDataListener extends DataListener {

    @Override
    public void connect() {
        System.out.println("Opening log file data source...");
    }

    @Override
    public String receiveRawData() {
        // just an example
        return "3,HeartRate,88.0,1710000002000";
    }
}