package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * To send the data with TCP socket
 * 
 * turn on the TCP server with certain port and if other application connects,
 * send the data in realtime.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * turn on the server and prepare
     * 
     * wait for the client connection and if connected , let able to send data
     * 
     * @param port the waiting TCP port number
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the data with TCP to the connective client
     * 
     * send it with form of CSV
     * and do nothing when noting connected
     * 
     * @param patientId the patient number
     * @param timestamp when the data generative
     * @param label     the kind of data
     * @param data      the actual data(String)
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
