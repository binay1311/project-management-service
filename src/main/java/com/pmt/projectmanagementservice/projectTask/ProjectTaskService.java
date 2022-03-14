package com.pmt.projectmanagementservice.projectTask;

import com.pmt.projectmanagementservice.backlog.Backlog;
import com.pmt.projectmanagementservice.backlog.BacklogRepository;
import com.pmt.projectmanagementservice.project.Project;
import com.pmt.projectmanagementservice.project.ProjectRepository;
import com.pmt.projectmanagementservice.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjecttask(String projectIdentifier, ProjectTask projectTask, String username) throws IllegalAccessException {

        //PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = projectService.getProjectByIdentifier(projectIdentifier, username).get().getBacklog();
//        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //Exceptions: Project not found
//            if(backlog == null){
//                throw new IllegalAccessException("ProjectTask creation failed since no backlog exists with key '"
//                + projectIdentifier + "'.");
//            }

        //set the BL to PT
        projectTask.setBacklog(backlog);
        //we want our project sequence like this : IDPRO1 ... 100 101
        Integer BacklogSequence = backlog.getPTSequence();
        // Update the BL sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //initial priority when priority is null
        if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }
        //initial status when status is null
        if(projectTask.getStatus() == null || projectTask.getStatus() == ""){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public List<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username) throws IllegalAccessException {
        String projectKey = projectIdentifier.toUpperCase();
//            Optional<Project> isProjectExists = projectRepository.findByProjectIdentifier(projectKey);
//            if(isProjectExists.isEmpty()){
//                throw new IllegalAccessException
//                        ("Backlog does not exists with project key " + projectKey);
//            }
        projectService.getProjectByIdentifier(projectKey, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectKey);
    }

    public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier,
                                                        String sequence, String username) throws IllegalAccessException {
        //make sure we are searching in the right backlog
        projectService.getProjectByIdentifier(projectIdentifier, username);
//            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
//            if(backlog == null){
//                throw new IllegalAccessException("Project with ID : '" + projectIdentifier + "' does not exist");
//            }

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null){
            throw new IllegalAccessException("Project Task '" + sequence + "' does not exist");
        }
        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(projectIdentifier)){
            throw new IllegalAccessException("Project Task '" + sequence +
                    "' does not exist in Project '" + projectIdentifier + "'");
        }

        return projectTask;
    }

    public ProjectTask updateProjectTaskByProjectSequence(
            ProjectTask updatedTask, String projectIdentifier, String sequence, String username) throws IllegalAccessException {
        ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, sequence, username);

        String summary = updatedTask.getSummary();
        String acceptanceCriteria = updatedTask.getAcceptanceCriteria();
        Date dueDate = updatedTask.getDueDate();
        String status = updatedTask.getStatus();
        Integer priority = updatedTask.getPriority();

        if(!Objects.equals(acceptanceCriteria, projectTask.getAcceptanceCriteria())){
            projectTask.setAcceptanceCriteria(acceptanceCriteria);
        }

        if(!Objects.equals(dueDate, projectTask.getDueDate())){
            projectTask.setDueDate(dueDate);
        }

        if(!Objects.equals(status, projectTask.getStatus())){
            projectTask.setStatus(status);
        }

        if(!Objects.equals(priority, projectTask.getPriority())){
            projectTask.setPriority(priority);
        }

        if(!Objects.equals(summary, projectTask.getSummary())){
            projectTask.setSummary(summary);
        }
//        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public String deleteProjectTaskByProjectSequence(String projectIdentifier, String sequence, String username) throws IllegalAccessException {
        ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, sequence, username);
        projectTaskRepository.delete(projectTask);
        return "Project task " + sequence + " was successfully deleted";
    }

}
