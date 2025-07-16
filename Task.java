package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public Task(String title, String description, LocalDate dueDate, String priority) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = "Chưa hoàn thành";
        this.createdAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
