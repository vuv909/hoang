package com.swp.server.services.job;

import com.swp.server.dto.JobCategoryDTO;
import com.swp.server.dto.JobDTO;
import com.swp.server.dto.SearchJobDtp;
import com.swp.server.dto.UpdateJobCategoryDTO;
import com.swp.server.dto.UpdateJobDTO;
import com.swp.server.entities.Job;

import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;

public interface JobService {
	public ResponseEntity<?> createJob(JobDTO jobDTO);

	public ResponseEntity<?> viewJob();

	public ResponseEntity<?> updateJob(JobDTO jobDTO);

	public ResponseEntity<?> getJobInforById(int Id);

	public ResponseEntity<?> createJobCategory(JobCategoryDTO jobDTO);

	ResponseEntity<?> updateJobCategory(UpdateJobCategoryDTO jobDTO);

	public ResponseEntity<?> deleteJobCategory(int jobId);

	public ResponseEntity<?> deleteJob(int jobId);

	ResponseEntity<?> viewJobCategory();

	public ResponseEntity<?> getAllBranch();

	public ResponseEntity<?> searchJob(SearchJobDtp searchJobDtp);
}
