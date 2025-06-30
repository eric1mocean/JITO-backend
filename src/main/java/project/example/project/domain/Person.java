package project.example.project.domain;

import jakarta.persistence.*;
import project.example.project.exceptions.DomainException;



@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String createDate;
    private String activityStatus;
    private String updateDate;
    private String image;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    void setId(Long id) {
        this.id = id;
    }

    Long getId() {
        return id;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new DomainException("Username cannot be null or empty.");
        }
        this.username = username;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new DomainException("Invalid email.");
        }
        this.email = email;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new DomainException("Password cannot be null or empty.");
        }
        this.password = password;
    }

    String getCreateDate() {
        return createDate;
    }

    void setCreateDate(String createDate) {
        if (createDate == null || !createDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new DomainException("Create date must be in format YYYY-MM-DD.");
        }
        this.createDate = createDate;
    }

    String getActivityStatus() {
        return activityStatus;
    }

    void setActivityStatus(String activityStatus) {
        if (activityStatus == null || activityStatus.trim().isEmpty()) {
            throw new DomainException("Activity status cannot be null or empty.");
        }
        this.activityStatus = activityStatus;
    }

    String getUpdateDate() {
        return updateDate;
    }

    void setUpdateDate(String updateDate) {
        if (updateDate == null || !updateDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new DomainException("Update date must be in format YYYY-MM-DD.");
        }
        this.updateDate = updateDate;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        if (image == null || image.trim().isEmpty()) {
            throw new DomainException("Image cannot be null or empty.");
        }
        this.image = image;
    }

    Task getTask() {
        return task;
    }

    void setTask(Task task) {
        this.task = task;
    }
}