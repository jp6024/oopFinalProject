package fitness;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String DATABASE_PATH = "activities.csv";
    private static final String TEMP_DATABASE_PATH = "activities_temp.csv";

    public static void saveActivity(String activityData) throws IOException {
        try (FileWriter fw = new FileWriter(DATABASE_PATH, true)) {
            fw.write(activityData + "\n");
            fw.flush();
        }
    }

    public static List<String> loadActivities() throws IOException {
        List<String> activities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                activities.add(line);
            }
        }
        return activities;
    }

    public static void updateActivity(int lineNumber, String updatedActivity) throws IOException {
        List<String> activities = loadActivities();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_DATABASE_PATH))) {
            int currentLine = 1;
            for (String activity : activities) {
                if (currentLine == lineNumber) {
                    writer.write(updatedActivity);
                } else {
                    writer.write(activity);
                }
                writer.newLine();
                currentLine++;
            }
        }
        Files.move(Paths.get(TEMP_DATABASE_PATH), Paths.get(DATABASE_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void deleteActivity(int lineNumber) throws IOException {
        List<String> activities = loadActivities();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_DATABASE_PATH))) {
            int currentLine = 1;
            for (String activity : activities) {
                if (currentLine != lineNumber) {
                    writer.write(activity);
                    writer.newLine();
                }
                currentLine++;
            }
        }
        Files.move(Paths.get(TEMP_DATABASE_PATH), Paths.get(DATABASE_PATH), StandardCopyOption.REPLACE_EXISTING);
    }
}

