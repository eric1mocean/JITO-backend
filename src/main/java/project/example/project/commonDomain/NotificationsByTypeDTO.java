package project.example.project.commonDomain;

import java.util.ArrayList;
import java.util.List;

import project.example.project.domain.Notification;

public class NotificationsByTypeDTO {
    private List<Notification> userNotifications = new ArrayList<>();
    private List<Notification> taskNotifications = new ArrayList<>();

    public List<Notification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(List<Notification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public List<Notification> getTaskNotifications() {
        return taskNotifications;
    }

    public void setTaskNotifications(List<Notification> taskNotifications) {
        this.taskNotifications = taskNotifications;
    }
}
