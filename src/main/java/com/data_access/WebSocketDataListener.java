package com.data_access;

/**
 * Listens for patient data from a WebSocket source.
 */
public class WebSocketDataListener extends DataListener {

    @Override
    public void connect() {
        System.out.println("Connecting to WebSocket data source...");
    }

    @Override
    public String receiveRawData() {
        // just an example
        return "2,Saturation,97.0,1710000001000";
    }
}