package manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.TaskDAO;

public class PersonalTaskManagerViolations {

    private LocalDate validateInput(String title, String dueDateStr, String priorityLevel) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Lỗi: Tiêu đề không được để trống.");
            return null;
        }
        if (dueDateStr == null || dueDateStr.trim().isEmpty()) {
            System.out.println("Lỗi: Ngày đến hạn không được để trống.");
            return null;
        }

        LocalDate dueDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dueDate = LocalDate.parse(dueDateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Định dạng ngày không hợp lệ.");
            return null;
        }

        String[] validPriorities = {"Thấp", "Trung bình", "Cao"};
        boolean isValidPriority = Arrays.asList(validPriorities).contains(priorityLevel);
        if (!isValidPriority) {
            System.out.println("Lỗi: Mức độ ưu tiên không hợp lệ.");
            return null;
        }

        return dueDate;
    }

    private boolean checkDuplicate(JSONArray tasks, String title, LocalDate dueDate) {
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            if (title.equalsIgnoreCase((String) task.get("title")) &&
                dueDate.toString().equals(task.get("due_date"))) {
                System.out.println("Lỗi: Nhiệm vụ trùng.");
                return true;
            }
        }
        return false;
    }

    public JSONObject addNewTaskWithViolations(String title, String description, String dueDateStr, String priorityLevel) {
        LocalDate dueDate = validateInput(title, dueDateStr, priorityLevel);
        if (dueDate == null) return null;

        TaskDAO dao = new TaskDAO();
        JSONArray tasks = dao.loadTasks();
        if (tasks == null) tasks = new JSONArray(); // đảm bảo không null

        if (checkDuplicate(tasks, title, dueDate)) return null;

        JSONObject newTask = new JSONObject();
        newTask.put("id", UUID.randomUUID().toString());
        newTask.put("title", title);
        newTask.put("description", description);
        newTask.put("due_date", dueDate.toString());
        newTask.put("priority", priorityLevel);
        newTask.put("status", "Chưa hoàn thành");
        newTask.put("created_at", LocalDateTime.now().toString());
        newTask.put("last_updated_at", LocalDateTime.now().toString());

        tasks.add(newTask);
        dao.saveTasks(tasks);

        System.out.println("Đã thêm nhiệm vụ.");
        return newTask;
    }
}
