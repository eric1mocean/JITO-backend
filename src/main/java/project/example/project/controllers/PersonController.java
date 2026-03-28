package project.example.project.controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Email;
import project.example.project.DomainExample;
import project.example.project.IRepositoryExample;
import project.example.project.commonDomain.CreateUserDTO;
import project.example.project.commonDomain.TaskDTO;
import project.example.project.commonDomain.UserLoginDataDTO;
import project.example.project.commonDomain.UserLoginRequestDTO;
import project.example.project.domain.Person;
import project.example.project.domain.Task;
import project.example.project.repository.PersonRepository;
import project.example.project.repository.TaskRepository;
import project.example.project.services.LoginLogger;
import project.example.project.services.RegisterLogger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/createUser")
    public ResponseEntity<Person> saveExample(@RequestBody CreateUserDTO createUserDTO) {
        Person entityAction = new Person();
        entityAction.setEmail(createUserDTO.getEmail());
        entityAction.setPassword(createUserDTO.getPassword());
        entityAction.setUsername(createUserDTO.getUsername());
        entityAction.setRole(createUserDTO.getRole());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String datestring = date.toString();
        entityAction.setCreateDate(datestring);
        personRepository.save(entityAction);
        RegisterLogger registerLogger = new RegisterLogger();
        registerLogger.logRegisterAction(entityAction.getEmail(), entityAction.getRole().toString());

        return new ResponseEntity<>(entityAction, HttpStatus.CREATED);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<Person>> getAllExamples() {
        List<Person> examples = personRepository.findAll();
        for (Person person : examples)
        {
            person.setTask(null);
        }
        return new ResponseEntity<>(examples, HttpStatus.OK);

    }

    @PutMapping("/assignTasks/{userId}/{taskId}")
    public boolean assignTask(@PathVariable Long userId, @PathVariable Long taskId) {
        Person user = personRepository.findById(userId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);
        if (user == null || task == null) {
            return false;
        }
        if (task.getUsers()==null){
            task.setUsers(new ArrayList<>());
            task.getUsers().add(user);
        }
        else {
            task.getUsers().add(user);
        }
        user.setTask(task);
        taskRepository.save(task);
        personRepository.save(user);

        return true;
    }

    @GetMapping("/getAllUserTasks/{userId}")
    public List<TaskDTO> getAllUserTasks(@PathVariable Long userId) {
        List<Task> tasks = new ArrayList<>();
        tasks = taskRepository.findAll();
        List<TaskDTO> userTasks = new ArrayList<>();
        for (Task task : tasks) {
            for (Person user : task.getUsers()) {
                if (user.getId().equals(userId)) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setTitle(task.getTitle());
                    taskDTO.setDescription(task.getDescription());
                    taskDTO.setDeadline(task.getDeadline());
                    taskDTO.setStatus(task.getStatus());
                    taskDTO.setSeverity(task.getSeverity());
                    userTasks.add(taskDTO);
                    break;
                }
            }
        }
        return userTasks;
    }

    @DeleteMapping("/deleteUser/{userId}")
    public boolean deleteUser(Long userId){
        personRepository.deleteById(userId);
        if (personRepository.findById(userId).isPresent()) {
            return false;
        }
        else return true;
    }

    @PostMapping("/loginUser/")
    public UserLoginDataDTO loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        System.out.println(userLoginRequestDTO.getEmail());
        System.out.println(userLoginRequestDTO.getPassword());
        Person user=getUserByEmail(userLoginRequestDTO.getEmail());
        if (user==null) return null;
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        if (!user.getPassword().equals(userLoginRequestDTO.getPassword())) return null;
        UserLoginDataDTO userLoginDataDTO = new UserLoginDataDTO();
        userLoginDataDTO.setId(user.getId());
        userLoginDataDTO.setUsername(user.getUsername());
        userLoginDataDTO.setEmail(user.getEmail());
        userLoginDataDTO.setImage(user.getImage());
        userLoginDataDTO.setRole(user.getRole());
        LoginLogger loginLogger = new LoginLogger();
        loginLogger.logLoginAction(userLoginDataDTO.getEmail(), userLoginDataDTO.getRole().toString()); 
        return userLoginDataDTO;


    }

    private Person getUserByEmail(String email) {

        for (Person user : personRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }
}
