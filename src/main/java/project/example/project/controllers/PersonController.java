package project.example.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.example.project.DomainExample;
import project.example.project.IRepositoryExample;
import project.example.project.domain.Person;
import project.example.project.repository.PersonRepository;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/createUser")
    public ResponseEntity<Person> saveExample(@RequestBody Person entityAction) {
        personRepository.save(entityAction);
        return new ResponseEntity<>(entityAction, HttpStatus.CREATED);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<Person>> getAllExamples() {
        List<Person> examples = personRepository.findAll();
        return new ResponseEntity<>(examples, HttpStatus.OK);
    }
}
