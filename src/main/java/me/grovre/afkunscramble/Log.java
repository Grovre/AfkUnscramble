package me.grovre.afkunscramble;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Log {

    public static boolean debug = AfkUnscramble.plugin.getConfig().getBoolean("debug");
    public static Queue<Log> logs;
    public static File logFile;
    public static BufferedWriter logWriter;
    public static Thread threadWriter;

    private String message;

    public static void loggerInit() throws IOException {
        if (!debug) return;

        logs = new ConcurrentLinkedQueue<>();
        String basePath = AfkUnscramble.plugin.getDataFolder().getAbsolutePath() + File.separator + "logs";
        logFile = new File(basePath);
        logFile.mkdir();
        int logNumber = -1;
        do {
            logNumber++;
            String fileName = String.format("logs%d.txt", logNumber);
            logFile = new File(basePath + File.separator + fileName);
        } while (logFile.exists());
        logWriter = new BufferedWriter(new FileWriter(logFile, false));

        Runnable writeToLog = () -> {
            while(debug) {
                try {
                    while(!logs.isEmpty()) {
                        String message = logs.poll().getMessage();
                        logWriter.write(message);
                    }
                    logWriter.flush();
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        threadWriter = new Thread(writeToLog);
        threadWriter.start();
    }

    public static void loggerClose() throws IOException {
        debug = false;
        while(!logs.isEmpty()) {
            String message = logs.poll().getMessage();
            logWriter.write(message);
        }
    }

    public Log(String message) {
        if(!debug) return;
        this.message = message;
        logs.add(this);

        System.out.println(
                "AfkUnscramble: " + message
        );
    }

    public static void forceExportLogs() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, false));
        for(Log log : logs) {
            bw.write(log.getMessage());
            bw.newLine();
        }

        bw.close();
    }

    public void writeLog() {

    }

    public static boolean getDebugStatus() {
        return debug;
    }

    public static Queue<Log> getLogs() {
        return logs;
    }

    public String getMessage() {
        return message;
    }
}
