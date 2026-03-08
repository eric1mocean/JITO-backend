package project.example.project.commonDomain;

import java.util.List;

public class GetTasksDTO {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private ETaskStatus taskStatus;
    private ETaskSeverity taskSeverity;
    private List<UserProperties> users;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    // Getter și Setter pentru taskName
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // Getter și Setter pentru taskDescription
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    // Getter și Setter pentru taskStatus
    public ETaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(ETaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    // Getter și Setter pentru taskSeverity
    public ETaskSeverity getTaskSeverity() {
        return taskSeverity;
    }

    public void setTaskSeverity(ETaskSeverity taskSeverity) {
        this.taskSeverity = taskSeverity;
    }

    public List<UserProperties> getUsers() {
        return users;
    }
    public void setUsers(List<UserProperties> users) {
        this.users = users;
    }





}



