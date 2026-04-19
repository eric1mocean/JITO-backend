package project.example.project.commonDomain;

public class ModifyTaskRequestDTO {
    private Long taskId;
    private String title;
    private String description;
    private String deadline;
    private ETaskSeverity severity;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public ETaskSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ETaskSeverity severity) {
        this.severity = severity;
    }
    
}
