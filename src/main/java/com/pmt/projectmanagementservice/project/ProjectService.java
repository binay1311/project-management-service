package com.pmt.projectmanagementservice.project;

import com.pmt.projectmanagementservice.backlog.Backlog;
import com.pmt.projectmanagementservice.user.User;
import com.pmt.projectmanagementservice.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Project> getProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public Optional<Project> getProjectByIdentifier(String projectId, String username) throws IllegalAccessException {
//        Optional<Project> project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        Optional<Project> project = projectRepository.findByProjectIdentifierAndProjectLeader(
                projectId.toUpperCase(), username);
        if(project.isEmpty()){
            throw new IllegalAccessException(
                    "Project with ProjectKey '" + projectId + "' does not exist");
        }
        if(!project.get().getProjectLeader().equals(username)){
            throw new IllegalAccessException(
                    "Project with ProjectKey '" + projectId + "' does not exist in your account");
        }
        return project;
    }

    public Project addProject(Project project, String username) throws IllegalAccessException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalAccessException("User '" + username + "' does not exist");
        }
        project.setUser(user.get());
        project.setProjectLeader(username);
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();

        Optional<Project> getProjectByIdentifier =
                projectRepository.findByProjectIdentifier(projectIdentifier);

        if (getProjectByIdentifier.isPresent()) {
            throw new IllegalAccessException(
                    "A project already exists with same projectKey");
        }

        if (project.getProjectName().length() <= 0
                || project.getProjectIdentifier().length() <= 0
                || project.getDescription().length() <= 0) {
            throw new IllegalAccessException("A project with empty parameters cant be saved");
        }

        project.setProjectIdentifier(projectIdentifier);

        Backlog backlog = new Backlog();
        project.setBacklog(backlog);
        backlog.setProject(project);
        backlog.setProjectIdentifier(project.getProjectIdentifier());

        log.info("Saving project with key " + project.getProjectIdentifier());
        return projectRepository.save(project);
    }

    public Project updateProject(Project project, String username) throws IllegalAccessException {
        Optional<Project> validateProject = getProjectByIdentifier(project.getProjectIdentifier(), username);
        Project getProject = validateProject.get();

        System.out.println("inside update " + project.getProjectName() + " " + project.getId());

        String projectName = project.getProjectName();
        String projectIdentifier = project.getProjectIdentifier();
        String description = project.getDescription();
        Date start_date = project.getStart_date() != null ? project.getStart_date() : null;
        Date end_date = project.getEnd_date() != null ? project.getEnd_date() : null;

//        Project getProject = projectRepository.findProjectByProjectIdentifier(projectIdentifier);

        log.info(getProject.getId() + " and " + getProject.getProjectIdentifier());

        if (projectName.length() == 0 || projectIdentifier.length() == 0 || description.length() == 0) {
            throw new IllegalStateException("A project with empty params cant be saved");
        }

        if(!getProject.getProjectName().equals(projectName)){
            getProject.setProjectName(projectName);
        }
        if(!getProject.getDescription().equals(description)){
            getProject.setDescription(description);
        }

        if(
                (getProject.getStart_date() == null && start_date != null)
                ||
                (getProject.getStart_date() != null &&
                        start_date != null &&
                        !getProject.getStart_date().equals(start_date))
        ){
            getProject.setStart_date(start_date);
        }

        if(
                (getProject.getEnd_date() == null && end_date != null)
                        ||
                        (getProject.getEnd_date() != null &&
                                end_date != null &&
                                !getProject.getEnd_date().equals(end_date))
        ){
            getProject.setEnd_date(end_date);
        }

        projectRepository.save(getProject);
        log.info("Project updated successfully");
        return project;
    }

    public Optional<Project> deleteProjectByIdentifier(String projectIdentifier, String username)
            throws IllegalAccessException {
        Optional<Project> project = getProjectByIdentifier(projectIdentifier, username);

//        Optional<Project> project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
//        if(project.isEmpty()){
//            throw new IllegalAccessException("Project with key '" + projectIdentifier + "' does not exist");
//        }

        projectRepository.delete(project.get());
        return project;
    }
}
