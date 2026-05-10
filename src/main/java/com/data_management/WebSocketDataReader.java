package com.data_management;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketDataReader implements DataReader {
  private String serverUrl;

  public WebSocketDataReader(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  @Override
  public void readData(DataStorage dataStorage) throws IOException {
    try {
      // Reset the websocket client and start connecting
      MyWebSocketClient client = new MyWebSocketClient(new URI(serverUrl), dataStorage);
      client.connect();

      System.out.println("Trying to connect to " + serverUrl);

    } catch (URISyntaxException e) {
      throw new IOException("Invalid WebSocket URL: " + serverUrl, e);
    }
  }
}
