package project.example.project.repository;

import org.springframework.stereotype.Repository;

import project.example.project.domain.Notification;
import project.example.project.domain.Person;
import project.example.project.domain.Task;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;




public interface NotificationRepository extends JpaRepository<Notification, Long> {
    

     
 

}
