package com.gdg.todo.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String title;
    private String desc;
    private String status;
    private int priority;
    private LocalDate dueD;
    private LocalDateTime createD;
    private LocalDateTime updateD;

    public Task() {}

    public Task(String id, String title, String desc, String status, int priority, LocalDate dueD, LocalDateTime createD, LocalDateTime updateD) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.priority = priority;
        this.dueD = dueD;
        this.createD = createD;
        this.updateD = updateD;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getStatus() { return status; }
    public int getPriority() { return priority; }
    public LocalDate getDueD() { return dueD; }
    public LocalDateTime getCreateD() { return createD; }
    public LocalDateTime getUpdateD() { return updateD; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setStatus(String status) { this.status = status; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setDueD(LocalDate dueD) { this.dueD = dueD; }
    public void setCreateD(LocalDateTime createD) { this.createD = createD; }
    public void setUpdateD(LocalDateTime updateD) { this.updateD = updateD; }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", dueD=" + dueD +
                ", createD=" + createD +
                ", updateD=" + updateD +
                '}';
    }
}
