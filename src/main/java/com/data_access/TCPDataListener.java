package com.data_access;

/**
 * Listens for patient data from a TCP source.
 */
public class TCPDataListener extends DataListener {

    @Override
    public void connect() {
        System.out.println("Connecting to TCP data source...");
    }

    @Override
    public String receiveRawData() {
        // just an example
        return "1,SystolicPressure,135.0,1710000000000";
    }

}
