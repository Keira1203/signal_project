package com.data_access;

/**
 * Coordinates data retrieval from listeners and parsing into usable objects.
 */
public class DataSourceAdapter {

    private DataParser dataParser;

    public DataSourceAdapter(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    public ParsedData handleIncomingData(DataListener listener) {
        listener.connect();
        String rawData = listener.receiveRawData();
        return dataParser.parse(rawData);
    }
}