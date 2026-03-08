package project.example.project.commonDomain;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String deadline;

    private ETaskStatus status;
    private ETaskSeverity severity;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public String getDeadline() {
        return deadline;
    }
    public void setStatus(ETaskStatus status) {
        this.status = status;
    }
    public ETaskStatus getStatus() {
        return status;
    }
    public void setSeverity(ETaskSeverity severity) {
        this.severity = severity;
    }
    public ETaskSeverity getSeverity() {
        return severity;
    }
}
