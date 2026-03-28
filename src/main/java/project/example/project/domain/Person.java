package project.example.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.example.project.exceptions.DomainException;



@Entity
@Setter
@Getter
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

    @Column(nullable = true)
    private String role = "developer";

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new DomainException("Username cannot be null or empty.");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new DomainException("Invalid email.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new DomainException("Password cannot be null or empty.");
        }
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        if (createDate == null || !createDate.matches("\\d{4}-\\d{2}-\\d{2}")) {

            throw new DomainException("Create date must be in format YYYY-MM-DD.");
        }
        this.createDate = createDate;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        if (activityStatus == null || activityStatus.trim().isEmpty()) {
            throw new DomainException("Activity status cannot be null or empty.");
        }
        this.activityStatus = activityStatus;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        if (updateDate == null || !updateDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new DomainException("Update date must be in format YYYY-MM-DD.");
        }
        this.updateDate = updateDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null || image.trim().isEmpty()) {
            throw new DomainException("Image cannot be null or empty.");
        }
        this.image = image;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {

        if (role.equals("admin") || role.equals("teamleader") || role.equals("developer") || role.equals("user")) this.role = role;
    }


}