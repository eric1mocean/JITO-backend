package project.example.project.controllers;

import org.apache.catalina.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.Email;
import project.example.project.DomainExample;
import project.example.project.IRepositoryExample;
import project.example.project.commonDomain.CreateUserDTO;
import project.example.project.commonDomain.TaskDTO;
import project.example.project.commonDomain.UserLoginDataDTO;
import project.example.project.commonDomain.UserLoginRequestDTO;
import project.example.project.domain.Notification;
import project.example.project.domain.Person;
import project.example.project.domain.Task;
import project.example.project.repository.NotificationRepository;
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
    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/createUser")
    public ResponseEntity<Person> saveExample(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        
        if (personRepository.existsByUsername(createUserDTO.getUsername())) 
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (personRepository.existsByEmail(createUserDTO.getEmail()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        
        
        Person entityAction = new Person();
        entityAction.setEmail(createUserDTO.getEmail());
        entityAction.setPassword(createUserDTO.getPassword());
        entityAction.setUsername(createUserDTO.getUsername());
        entityAction.setRole(createUserDTO.getRole());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String datestring = date.toString();
        entityAction.setCreateDate(datestring);
        entityAction.setActive(false);

        personRepository.save(entityAction);
        Notification notification = new Notification();
        notification.setLabel("User Registered");
        notification.setDescription("User with name " + entityAction.getUsername() + " and email " + entityAction.getEmail() + " has registered and is awaiting activation.");
        notification.setActionDate(LocalDate.now().toString());
        notification.setNotificationType(project.example.project.commonDomain.ENotification.USER_RELATED);
        notificationRepository.save(notification);

        RegisterLogger registerLogger = new RegisterLogger();
        registerLogger.logRegisterAction(entityAction.getEmail(), entityAction.getRole().toString());

        return new ResponseEntity<>(entityAction, HttpStatus.CREATED);
    }

    @GetMapping("/getUsers/{keyword}")
    public ResponseEntity<List<Person>> getAllExamples(@PathVariable String keyword) {
        List<Person> examples = personRepository.findAll();
        for (Person person : examples)
        {
            person.setTask(null);
        }
        List<Person> filteredExamples = new ArrayList<>();
        for (Person person : examples) {
            if (person.getUsername().toLowerCase().contains(keyword.toLowerCase()) || person.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                filteredExamples.add(person);
            }
        }
        return new ResponseEntity<>(filteredExamples, HttpStatus.OK);
    }
    @GetMapping("/getAllInactiveUsers/{userId}/{keyword}") 
    public List<Person> getAllInactiveUsers(@PathVariable Long userId, @PathVariable String keyword) {

        Person user = personRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        }
        if (user.getRole().equals("admin")) {
            List<Person> users = personRepository.findAll();
            List<Person> inactiveUsers = new ArrayList<>();
            for (Person p : users) {
                if (p.getActive() == false && (p.getUsername().toLowerCase().contains(keyword.toLowerCase()) || p.getEmail().toLowerCase().contains(keyword.toLowerCase()))) {
                    p.setTask(null);
                    inactiveUsers.add(p);
                }
            }
            return inactiveUsers;
            
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be an admin to perform this operation.");
        }
        
    }

    @PutMapping("/approveUserActivationRequest/{userToActivateId}/{userActivatingId}") 
        public void approveUserActivationRequest(@PathVariable Long userToActivateId, @PathVariable Long userActivatingId) {
        Person userActivating = personRepository.findById(userActivatingId).orElse(null);
        if (userActivating == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        }
        
        if (userActivating.getRole().equals("admin")) {
           Person userToActivate = personRepository.findById(userToActivateId).orElse(null);
            if (userToActivate == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User to activate not found.");
            }
            Notification notification = new Notification();
            notification.setLabel("User Activation Request Approved");
            notification.setDescription("User with name " + userToActivate.getUsername() + " and email " + userToActivate.getEmail() + " has been activated.");
            notification.setActionDate(LocalDate.now().toString());
            notification.setNotificationType(project.example.project.commonDomain.ENotification.USER_RELATED);
            notificationRepository.save(notification);

            userToActivate.setActive(true);
            personRepository.save(userToActivate);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be an admin to perform this operation.");
        }
        
        
    }
    
    

    @PutMapping("/assignTasks/{userId}/{taskId}")
    public boolean assignTask(@PathVariable Long userId, @PathVariable Long taskId) {
        Person user = personRepository.findById(userId).orElse(null);
        Task task = taskRepository.findById(taskId).orElse(null);
        for (Person p : task.getUsers())
        {
            if (user.getId().equals(p.getId()))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This task has already been assigned to this user.");
            }
        }
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

    @DeleteMapping("/deleteUser/{userToDeleteId}/{userDeletingId}")
    public boolean deleteUser(@PathVariable Long userToDeleteId, @PathVariable Long userDeletingId) {
        Person userDeleting = personRepository.findById(userDeletingId).orElse(null);
        if (userDeleting.getRole().equals("admin"))
        {
            Person userToDelete = personRepository.findById(userToDeleteId).orElse(null);
            if (userToDelete == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User to delete not found.");
            }
            
            personRepository.deleteById(userToDeleteId);
            
            if (personRepository.findById(userToDeleteId).isPresent()) {
                return false;
            }
            else 
            {
                Notification notification = new Notification();
                notification.setLabel("User Deleted");
                notification.setDescription("User with name " + userToDelete.getUsername() + " and email " + userToDelete.getEmail() + " has been deleted.");
                    return true;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be an admin to perform this operation.");

    }

    @PostMapping("/loginUser/")
    public UserLoginDataDTO loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        System.out.println(userLoginRequestDTO.getEmail());
        System.out.println(userLoginRequestDTO.getPassword());
        Person user=getUserByEmail(userLoginRequestDTO.getEmail());
        if (user==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        if (!user.getActive()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not active yet. Please wait for admin approval.");
        if (!user.getPassword().equals(userLoginRequestDTO.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"); 
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
