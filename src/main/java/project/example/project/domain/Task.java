package project.example.project.domain;

import jakarta.persistence.*;
import project.example.project.commonDomain.ETaskSeverity;
import project.example.project.commonDomain.ETaskStatus;
import project.example.project.exceptions.DomainException;

import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String deadline;

    private ETaskStatus status;
    private ETaskSeverity severity;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Person> users;

    // Getters și setters

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new DomainException("Task title cannot be null or empty.");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new DomainException("Task description cannot be null or empty.");
        }
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        if (deadline == null || !deadline.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new DomainException("Deadline must be in format YYYY-MM-DD.");
        }
        this.deadline = deadline;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        if (status == null) {
            throw new DomainException("Task status cannot be null.");
        }
        this.status = status;
    }

    public ETaskSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ETaskSeverity severity) {
        if (severity == null) {
            throw new DomainException("Task severity cannot be null.");
        }
        this.severity = severity;
    }

    public List<Person> getUsers() {
        return users;
    }

    void setUsers(List<Person> users) {
        this.users = users;
    }
}

