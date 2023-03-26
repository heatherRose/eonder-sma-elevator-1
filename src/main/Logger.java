package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
    private ArrayList<String> log;
    private static final String LOG_FILE_NAME = "elevator_log.txt";

    public Logger() {
        this.log = new ArrayList<String>();
    }
    
    public void logRequest(String request) {
        String logEntry = "[" + new Date() + "] Requested floor " + request;
        log.add(logEntry);
        System.out.println(logEntry);
    }

    public void logStop(int floor) {
        String logEntry = "[" + new Date() +  "] Stopped at floor " + floor;
        log.add(logEntry);
        System.out.println(logEntry);
    }
    
    public void logPass(int floor) {
        String logEntry = "[" + new Date() +  "] Passed floor " + floor;
        log.add(logEntry);
        System.out.println(logEntry);
    }

    public void writeLogToFile() {
        try {
            FileWriter writer = new FileWriter(LOG_FILE_NAME);

            for (String logEntry : log) {
                writer.write(logEntry + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
