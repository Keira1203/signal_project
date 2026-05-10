package com.data_management;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * WebSocketDataReader is responsible for connecting to a WebSocket server, receiving data messages,
 * parsing them, and storing the relevant information in the DataStorage. It implements the
 * DataReader
 */
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
      boolean connected = client.connectBlocking();
      if (!connected) {
        throw new IOException("Failed to connect to WebSocket server at: " + serverUrl);
      }

    } catch (URISyntaxException e) {
      throw new IOException("Invalid WebSocket URL: " + serverUrl, e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Restore interrupted status
      throw new IOException("WebSocket connection was interrupted", e);
    }
  }
}
