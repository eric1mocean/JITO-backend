package project.example.project.commonDomain;

import java.util.List;

public class UserWithUnassignedTasks {
    public List<UserProperties> users;
    public List<TaskDTO> tasks;

    public void setUsers(List<UserProperties> users) {
        this.users = users;
    }
    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
    public List<UserProperties> getUsers() {
        return users;
    }
    public List<TaskDTO> getTasks() {
        return tasks;
    }

}
