package project.example.project.commonDomain;

public class ChangeStatusRequestDTO {
    private Long userId;
    private Long taskId;
    private ETaskStatus newStatus;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public Long getTaskId() {
        return taskId;
    }
    public void setNewStatus(ETaskStatus newStatus) {
        this.newStatus = newStatus;
    }
    public ETaskStatus getNewStatus() {
        return newStatus;
    }

}
