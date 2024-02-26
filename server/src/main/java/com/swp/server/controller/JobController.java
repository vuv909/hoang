package com.swp.server.controller;

import com.swp.server.dto.JobCategoryDTO;
import com.swp.server.dto.JobDTO;
import com.swp.server.dto.SearchJobDtp;
import com.swp.server.dto.UpdateJobCategoryDTO;
import com.swp.server.dto.UpdateJobDTO;
import com.swp.server.repository.JobApplyRepo;
import com.swp.server.repository.JobCategoryRepo;
import com.swp.server.repository.JobRepo;
import com.swp.server.services.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/job")
public class JobController {

	@Autowired
	private JobApplyRepo jobApplyRepo;

	@Autowired
	private JobCategoryRepo jobCategoryRepo;

	@Autowired
	private JobRepo jobRepo;

	@Autowired
	private JobService jobService;
	
	
	@PostMapping("/searchJob")
	public ResponseEntity<?> searchJob(@RequestBody SearchJobDtp searchJobDtp) {
		System.err.print(searchJobDtp);
		return jobService.searchJob(searchJobDtp);
	}

	@GetMapping("/viewJob")
	public ResponseEntity<?> viewJob() {
		return jobService.viewJob();
	}

	@GetMapping("/viewJobCategory")
	public ResponseEntity<?> viewJobCategory() {
		return jobService.viewJobCategory();
	}

	@GetMapping("/viewBranch")
	public ResponseEntity<?> viewbranch() {
		return jobService.getAllBranch();
	}

	@PostMapping("/createJob")
	public ResponseEntity<?> createJob(@ModelAttribute JobDTO jobDTO) {
		return jobService.createJob(jobDTO);
	}

	@DeleteMapping("/deleteJob/{jobId}")
	public ResponseEntity<?> deleteJob(@PathVariable int jobId) {
		return jobService.deleteJob(jobId);
	}

	@GetMapping("/view/{jobId}")
	public ResponseEntity<?> viewJobInfor(@PathVariable int jobId) {
		return jobService.getJobInforById(jobId);
	}

	@PutMapping("/edit")
	public ResponseEntity<?> updateJob(@ModelAttribute JobDTO jobDTO) {
		return jobService.updateJob(jobDTO);
	}

	@GetMapping("/branch/getAll")
	public ResponseEntity<?> getAllBranch() {
		return jobService.getAllBranch();
	}

	@PostMapping("/job_category")
	public ResponseEntity<?> createJobCategory(@ModelAttribute JobCategoryDTO jobDTO) {
		return jobService.createJobCategory(jobDTO);
	}

	@PostMapping("/update/job_category")
	public ResponseEntity<?> updateJob(@ModelAttribute UpdateJobCategoryDTO jobDTO) {
		return jobService.updateJobCategory(jobDTO);
	}

	@DeleteMapping("/delete/job_category/{jobId}")
	public ResponseEntity<?> deleteJobCategory(@PathVariable int jobId) {
		return jobService.deleteJobCategory(jobId);
	}

}
