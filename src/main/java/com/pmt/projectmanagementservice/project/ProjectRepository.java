package com.pmt.projectmanagementservice.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByProjectIdentifier(String projectId);

    Optional<Project> findByProjectIdentifierAndProjectLeader(String projectId, String username);

    Project findProjectByProjectIdentifier(String projectIdentifier);

    List<Project> findAllByProjectLeader(String username);

}
