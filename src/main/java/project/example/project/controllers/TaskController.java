package project.example.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.example.project.commonDomain.*;
import project.example.project.domain.Person;
import project.example.project.domain.Task;
import project.example.project.repository.PersonRepository;
import project.example.project.repository.TaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        Task entityAction = new Task();
        entityAction.setTitle(createTaskDTO.getTitle());
        entityAction.setDescription(createTaskDTO.getDescription());
        entityAction.setDeadline(createTaskDTO.getDeadline());
        entityAction.setSeverity(createTaskDTO.getSeverity());
        entityAction.setStatus(ETaskStatus.PENDING);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String datestring = date.toString();
        taskRepository.save(entityAction);
        return new ResponseEntity<>(entityAction, HttpStatus.CREATED);
    }

    @GetMapping("/getTasks")
    public ResponseEntity<List<GetTasksDTO>> getAllTasks() {
        List<GetTasksDTO> tasksDTO = getTasks();
        return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
    }

    @PutMapping("/changeStatus")
    public boolean changeStatus(ChangeStatusRequestDTO changeStatusRequestDTO){

        Task task = taskRepository.findById(changeStatusRequestDTO.getTaskId()).orElse(null);
        if (task == null) {
            return false;
        }
        for (Person user : task.getUsers()){
            if (user.getId().equals(changeStatusRequestDTO.getUserId())){
                task.setStatus(changeStatusRequestDTO.getNewStatus());
                taskRepository.save(task);
                return true;
            }
        }

        return false;
    }
    @DeleteMapping("/deleteTask/{taskId}")
    public boolean deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
        if (taskRepository.findById(taskId).isPresent()) {
            return false;
        }
        else return true;
    }

    @GetMapping("/searchByKeyWord/{keyWord}")
    public List<TaskDTO> searchTaskByKeyWord(String keyWord){
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(keyWord.toLowerCase()) || task.getDescription().toLowerCase().contains(keyWord.toLowerCase())){
                TaskDTO getTasksDTO = new TaskDTO();
                getTasksDTO.setId(task.getId());
                getTasksDTO.setTitle(task.getTitle());
                getTasksDTO.setDescription(task.getDescription());
                getTasksDTO.setStatus(task.getStatus());
                getTasksDTO.setSeverity(task.getSeverity());
                matches.add(getTasksDTO);
            }
        }
        return matches;
    }

    @GetMapping("/getTask/{taskId}")
    public ResponseEntity<GetTasksDTO> getTask(@PathVariable Long taskId){
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
        GetTasksDTO getTasksDTO = new GetTasksDTO();
        getTasksDTO.setTaskName(task.getTitle());
        getTasksDTO.setTaskId(taskId);
        getTasksDTO.setTaskStatus(task.getStatus());
        getTasksDTO.setTaskDescription(task.getDescription());
        getTasksDTO.setTaskSeverity(task.getSeverity());
        getTasksDTO.setUsers(new ArrayList<>());
        for (Person user : task.getUsers()){
            UserProperties userProperties = new UserProperties();
            userProperties.setUserEmail(user.getEmail());
            userProperties.setUserImage(user.getImage());
            userProperties.setUserName(user.getUsername());
            userProperties.setUserId(user.getId());
            getTasksDTO.getUsers().add(userProperties);
        }
        return new ResponseEntity<>(getTasksDTO, HttpStatus.OK);

    }

    private List<GetTasksDTO> getTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<GetTasksDTO> tasksDTO = new ArrayList<>();
        for (Task task : tasks) {
            GetTasksDTO getTasksDTO = new GetTasksDTO();
            getTasksDTO.setTaskId(task.getId());
            getTasksDTO.setTaskName(task.getTitle());
            getTasksDTO.setTaskDescription(task.getDescription());
            getTasksDTO.setTaskStatus(task.getStatus());
            getTasksDTO.setTaskSeverity(task.getSeverity());
            List<Person> users = task.getUsers();
            List<UserProperties> list = new ArrayList<>();
            for (Person user : users) {
                UserProperties userProperties = new UserProperties();
                userProperties.setUserId(user.getId());
                userProperties.setUserName(user.getUsername());
                userProperties.setUserEmail(user.getEmail());
                userProperties.setUserImage(user.getImage());
                list.add(userProperties);
            }
            getTasksDTO.setUsers(list);
            tasksDTO.add(getTasksDTO);
        }
        return tasksDTO;

    }

    @GetMapping("/getUnassignedTasks")
    public List<GetTasksDTO> getUnassignedTasks(){
        List<GetTasksDTO> allTasks = getTasks();
        List<GetTasksDTO> unassignedTasks = new ArrayList<>();
        for (GetTasksDTO getTasksDTO : allTasks) {
            if (getTasksDTO.getUsers().size()<3) {
                unassignedTasks.add(getTasksDTO);
            }
        }
        return unassignedTasks;
    }


    @GetMapping("/getFilteredTasks")
    public FilteredTasksDTO getFilteredTasks(){
        List<GetTasksDTO> allTasks = getTasks();
        FilteredTasksDTO filteredTasksDTO = new FilteredTasksDTO();
        for (GetTasksDTO getTasksDTO : allTasks) {
            if (getTasksDTO.getTaskStatus() == ETaskStatus.PENDING) {
                filteredTasksDTO.getTasksDTOPending.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.COMPLETED) {
                filteredTasksDTO.getTasksDTOCompleted.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.INPROGRESS) {
                filteredTasksDTO.getTasksDTOInProgress.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.OPEN) {
                filteredTasksDTO.getTasksDTOOpen.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.REJECTED) {
                filteredTasksDTO.getTasksDTORejected.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.CANCELLED) {
                filteredTasksDTO.getTasksDTOCancelled.add(getTasksDTO);
            }
            else if (getTasksDTO.getTaskStatus() == ETaskStatus.ONHOLD) {
                filteredTasksDTO.getTasksDTOOnHold.add(getTasksDTO);
            }
        }
        return filteredTasksDTO;
    }







}
