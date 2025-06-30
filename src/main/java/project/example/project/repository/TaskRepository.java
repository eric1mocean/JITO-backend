package project.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.example.project.domain.Task;



public interface TaskRepository extends JpaRepository<Task, Long> {

}


