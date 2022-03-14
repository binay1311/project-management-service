package com.pmt.projectmanagementservice.backlog;

import com.pmt.projectmanagementservice.constants.StatusCode;
import com.pmt.projectmanagementservice.models.Response;
import com.pmt.projectmanagementservice.projectTask.ProjectTask;
import com.pmt.projectmanagementservice.projectTask.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/{backlog_id}")
    public Response<?> addProjectTaskToBacklog(@RequestBody ProjectTask projectTask,
                                               @PathVariable String backlog_id,
                                               Principal principal) throws IllegalAccessException {
        return Response.<ProjectTask>builder()
                .response(projectTaskService.addProjecttask(backlog_id, projectTask, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/{projectIdentifier}")
    public Response<?> getProjectBacklog(@PathVariable String projectIdentifier,
                                         Principal principal) throws IllegalAccessException {
        return Response.<List<ProjectTask>>builder()
                .response(projectTaskService.findBacklogByProjectIdentifier(projectIdentifier, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/{projectIdentifier}/{projectTask_sequence}")
    public Response<?> getProjectTask(@PathVariable String projectIdentifier,
                                      @PathVariable String projectTask_sequence,
                                      Principal principal) throws IllegalAccessException {
        return Response.<ProjectTask>builder()
                .response(projectTaskService.
                        findProjectTaskByProjectSequence(projectIdentifier, projectTask_sequence, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @PatchMapping("/{projectIdentifier}/{projectTask_sequence}")
    public Response<?> updateProjectTask(
            @RequestBody ProjectTask projectTask,
            @PathVariable String projectIdentifier,
            @PathVariable String projectTask_sequence,
            Principal principal) throws IllegalAccessException {

        return Response.<ProjectTask>builder()
                .response(
                        projectTaskService.updateProjectTaskByProjectSequence(
                                projectTask,
                                projectIdentifier,
                                projectTask_sequence,
                                principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @DeleteMapping("/{projectIdentifier}/{projectTask_sequence}")
    public Response<?> deleteProjectTask(
            @PathVariable String projectIdentifier,
            @PathVariable String projectTask_sequence,
            Principal principal) throws IllegalAccessException {

        return Response.<String>builder()
                .response(
                        projectTaskService.deleteProjectTaskByProjectSequence(
                                projectIdentifier, projectTask_sequence, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

}
