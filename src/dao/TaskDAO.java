package dao;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TaskDAO {
    private static final String DB_FILE_PATH = "tasks_database.json";

    public JSONArray loadTasks() {
        try (FileReader reader = new FileReader(DB_FILE_PATH)) {
            Object obj = new JSONParser().parse(reader);
            return obj instanceof JSONArray ? (JSONArray) obj : new JSONArray();
        } catch (IOException | ParseException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }
        return new JSONArray();
    }

    public void saveTasks(JSONArray tasks) {
        try (FileWriter writer = new FileWriter(DB_FILE_PATH)) {
            writer.write(tasks.toJSONString());
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}