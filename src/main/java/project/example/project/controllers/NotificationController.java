package project.example.project.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import project.example.project.commonDomain.ENotification;
import project.example.project.commonDomain.NotificationsByTypeDTO;
import project.example.project.domain.Notification;
import project.example.project.repository.NotificationRepository;

@CrossOrigin(origins = "*")
@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/getNotifications")
    public NotificationsByTypeDTO getNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        notifications.sort(Comparator.comparing(Notification::getActionDate).reversed());

        List<Notification> userNotifications = new ArrayList<>();
        List<Notification> taskNotifications = new ArrayList<>();

        for (Notification notification : notifications) {
            if (notification.getNotificationType() == ENotification.USER_RELATED) {
                userNotifications.add(notification);
            } else if (notification.getNotificationType() == ENotification.TASK_RELATED) {
                taskNotifications.add(notification);
            }
        }

        NotificationsByTypeDTO response = new NotificationsByTypeDTO();
        response.setUserNotifications(userNotifications);
        response.setTaskNotifications(taskNotifications);

        return response;
    }

    @GetMapping("/getNotifcations")
    public NotificationsByTypeDTO getNotificationsLegacyAlias() {
        return getNotifications();
    }
}
