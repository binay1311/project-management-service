package com.pmt.projectmanagementservice.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmt.projectmanagementservice.backlog.Backlog;
import com.pmt.projectmanagementservice.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Project")
public class Project {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @SequenceGenerator(
        name = "project_sequence",
        sequenceName = "project_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "project_sequence"
    )
    private Long Id;

//    @Id
    private String projectIdentifier;

    private String projectName;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_At;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updated_At;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }

}


//    @Column(nullable = false, length = 5, updatable = false)
//    @NotBlank(message = "Project Identifier is required")
//    @Size(min=4, max=5, message = "Please use 4-5 characters")
//    @Column(updatable = false, unique = true)
