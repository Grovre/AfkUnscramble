package me.grovre.afkunscramble;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Log {

    public static boolean debug = AfkUnscramble.plugin.getConfig().getBoolean("debug");
    public static List<Log> logs = new ArrayList<>();

    private String message;

    public Log(String message) {
        if(!debug) return;
        this.message = message;
        logs.add(this);

        System.out.println(
                "AfkUnscramble: " + message
        );
    }

    public void cleanLogs() {
        if(logs.size() > 100) {
            if(debug) {
                try {
                    exportLogs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logs.clear();
        }
    }

    public static void exportLogs() throws IOException {
        File logFile = new File(AfkUnscramble.plugin.getDataFolder().getAbsolutePath() + "logs.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, false));

        for(Log log : logs) {
            bw.write(log.getMessage());
            bw.newLine();
        }

        bw.close();
    }

    public static boolean getDebugStatus() {
        return debug;
    }

    public static List<Log> getLogs() {
        return logs;
    }

    public String getMessage() {
        return message;
    }
}
