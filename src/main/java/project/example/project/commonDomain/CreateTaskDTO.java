package project.example.project.commonDomain;

public class CreateTaskDTO {

    private String title;
    private String description;
    private String deadline;
    private ETaskSeverity severity;

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
    public void setSeverity(ETaskSeverity severity) {
        this.severity = severity;
    }
    public ETaskSeverity getSeverity() {
        return severity;
    }

}
