package project.example.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerExample {

    @Autowired
    private IRepositoryExample repositoryExample;

    @PostMapping("/example")
    public ResponseEntity<DomainExample> saveExample(@RequestBody DomainExample entityAction) {
        repositoryExample.save(entityAction); 
        return new ResponseEntity<>(entityAction, HttpStatus.CREATED); 
    }

    @GetMapping("/example")
    public ResponseEntity<List<DomainExample>> getAllExamples() {
        List<DomainExample> examples = repositoryExample.findAll();
        return new ResponseEntity<>(examples, HttpStatus.OK); 
    }
}
