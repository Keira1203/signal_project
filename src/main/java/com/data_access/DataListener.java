package com.data_access;

/**
 * Abstract listener for receiving raw patient data from an external source.
 */
public abstract class DataListener {

    public abstract void connect();

    public abstract String receiveRawData();

}
