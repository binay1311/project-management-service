package com.pmt.projectmanagementservice.project;

import com.pmt.projectmanagementservice.constants.StatusCode;
import com.pmt.projectmanagementservice.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public Response<?> getAllProjects(Principal principal){
        return Response.<List<Project>>builder()
                .response(projectService.getProjects(principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/{projectIdentifier}")
    public Response<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal)
            throws IllegalAccessException {
        return Response.<Optional<Project>>builder()
                .response(projectService.getProjectByIdentifier(projectIdentifier, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/add")
    public Response<?> addProject(@RequestBody Project project, Principal principal)
            throws IllegalAccessException {
        return Response.<Project>builder()
                .response(projectService.addProject(project, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @PutMapping("/update")
    public Response<?> updateProject(@RequestBody Project project, Principal principal)
            throws IllegalStateException, IllegalAccessException {
        return Response.<Project>builder()
                .response(projectService.updateProject(project, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

    @DeleteMapping("/{projectIdentifier}")
    public Response<?> deleteProject(@PathVariable String projectIdentifier, Principal principal)
            throws IllegalAccessException {
        return Response.<Optional<Project>>builder()
                .response(projectService.deleteProjectByIdentifier(projectIdentifier, principal.getName()))
                .responseCode(StatusCode.SUCCESS.getCode())
                .responseMessage(StatusCode.SUCCESS.getMessage())
                .build();
    }

}
