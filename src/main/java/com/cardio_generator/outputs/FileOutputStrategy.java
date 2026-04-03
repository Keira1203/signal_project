package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TO save the data to the file
 * 
 * make independent file for each data and save it .
 * the output directory is made automatically
 */
public class FileOutputStrategy implements OutputStrategy {

    // the name have to start with the lower case
    private String baseDirectory;

    // no underline is allowed
    // change the visibility to private because this is the internal class state
    // for the second word should be upper case
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * set the directory for file output and initialize
     * 
     * @param baseDirectory the path of the saving folder if no exist, made
     *                      automatically
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * extra add to the certain patients file
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // variable name should start with lower case letter
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}