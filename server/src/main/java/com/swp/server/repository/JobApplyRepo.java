package com.swp.server.repository;

import com.swp.server.entities.Job;
import com.swp.server.entities.Job_Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplyRepo extends JpaRepository<Job_Application,Integer> {
    Optional<Job_Application> findByJob(Job job);
}
