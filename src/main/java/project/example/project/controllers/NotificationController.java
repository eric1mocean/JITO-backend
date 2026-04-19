package project.example.project.controllers;

import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;
import project.example.project.commonDomain.ENotification;
import project.example.project.commonDomain.FilteredNotifications;
import project.example.project.domain.Notification;
import project.example.project.domain.Person;
import project.example.project.repository.NotificationRepository;
import project.example.project.repository.PersonRepository;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired 
    private PersonRepository userRepository;


    @GetMapping ("/notifications/{userId}")
    public Object getNotificationsForUser(@PathVariable Long userId) {
        Person user = userRepository.findById(userId).orElse(null);
        if (userId == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
        if (!user.getRole().equals("teamleader"))
        {
            throw new IllegalArgumentException("Only team leaders can view notifications.");
        }
        List<Notification> notifications = notificationRepository.findAll();
        List<Notification> seenUserNotifications = new ArrayList<>();
        List<Notification> unseenUserNotifications = new ArrayList<>();
        List<Notification> seenTaskNotifications = new ArrayList<>();
        List<Notification> unseenTaskNotifications = new ArrayList<>();
        
        for (Notification notification : notifications) {
            if (notification.getRead()==true && notification.getNotificationType() == ENotification.USER_RELATED) {
                seenUserNotifications.add(notification);
            } else if (notification.getRead()==false && notification.getNotificationType() == ENotification.USER_RELATED) {
                unseenUserNotifications.add(notification);
            } else if (notification.getRead()==true && notification.getNotificationType() == ENotification.TASK_RELATED) {
                seenTaskNotifications.add(notification);
            } else if (notification.getRead()==false && notification.getNotificationType() == ENotification.TASK_RELATED) {
                unseenTaskNotifications.add(notification);
            }
        }
        return new Object() {
            public List<Notification> seenUserNotifications = seenUserNotifications;
            public List<Notification> unseenUserNotifications = unseenUserNotifications;
            public List<Notification> seenTaskNotifications = seenTaskNotifications;
            public List<Notification> unseenTaskNotifications = unseenTaskNotifications;
        };
        
    } 
}


