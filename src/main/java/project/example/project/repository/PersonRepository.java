package project.example.project.repository;

import org.springframework.stereotype.Repository;
import project.example.project.domain.Person;
import project.example.project.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PersonRepository extends JpaRepository<Person, Long> {

}

