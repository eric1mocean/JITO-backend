package project.example.project.commonDomain;

import java.util.ArrayList;
import java.util.List;

public class FilteredTasksDTO {

    public List<GetTasksDTO> getTasksDTOPending;
    public List<GetTasksDTO> getTasksDTOCompleted;
    public List<GetTasksDTO> getTasksDTOInProgress;
    public List<GetTasksDTO> getTasksDTOOpen;
    public List<GetTasksDTO> getTasksDTORejected;
    public List<GetTasksDTO> getTasksDTOCancelled;
    public List<GetTasksDTO> getTasksDTOOnHold;

    public FilteredTasksDTO(){
        getTasksDTOPending = new ArrayList<GetTasksDTO>();
        getTasksDTOCompleted =  new ArrayList<GetTasksDTO>();
        getTasksDTOInProgress = new ArrayList<GetTasksDTO>();
        getTasksDTOOpen = new ArrayList<GetTasksDTO>();
        getTasksDTORejected = new ArrayList<GetTasksDTO>();
        getTasksDTOCancelled = new ArrayList<GetTasksDTO>();
        getTasksDTOOnHold = new ArrayList<GetTasksDTO>();
    }

}
