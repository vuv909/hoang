package com.swp.server.services.job;

import com.swp.server.dto.BranchDTO;
import com.swp.server.dto.JobCateDTO;
import com.swp.server.dto.JobCategoryDTO;
import com.swp.server.dto.JobDTO;
import com.swp.server.dto.SearchJobDtp;
import com.swp.server.dto.UpdateJobCategoryDTO;
import com.swp.server.entities.Branch;
import com.swp.server.entities.Job;
import com.swp.server.entities.Job_Category;
import com.swp.server.repository.BranchRepo;
import com.swp.server.repository.JobApplyRepo;
import com.swp.server.repository.JobCategoryRepo;
import com.swp.server.repository.JobRepo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class JobServiceImpl implements JobService {
	@Autowired
	private JobApplyRepo jobApplyRepo;

	@Autowired
	private JobCategoryRepo jobCategoryRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private JobRepo jobRepo;

	@Override
	public ResponseEntity<?> searchJob(SearchJobDtp searchJobDtp) {
		// Extract search criteria from SearchJobDtp
		System.err.print(searchJobDtp.toString());
		String text = searchJobDtp.getText();
		int category = searchJobDtp.getCategory();
		int branch = searchJobDtp.getBranch();
		String careerLevel = searchJobDtp.getCareer_level();
		int experience = searchJobDtp.getExperience();
		String salary = searchJobDtp.getSalary();
		String qualification = searchJobDtp.getQualification();

		// Call the repository method with search criteria
		List<Job> jobList = jobRepo.findByCriteria(text, category, branch, careerLevel, experience, salary,
				qualification);

		// Check if the job list is empty
		if (jobList.isEmpty()) {
			// If the list is empty, return a ResponseEntity with an error message
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No jobs found matching the search criteria.");
		} else {
			// If the list is not empty, return a ResponseEntity with the job list
			return ResponseEntity.ok(jobList);
		}
	}

	@Override
	public ResponseEntity<?> createJobCategory(JobCategoryDTO jobDTO) {
		try {

			if (jobDTO.getImage() == null || jobDTO.getName() == null || jobDTO.getName().trim() == "") {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Không được để trống trường nào trong job category !!!");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			} else {
				Date date = new Date();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				Job_Category job_Category = new Job_Category();
				job_Category.setImage(jobDTO.getImage().getBytes());
				job_Category.setName(jobDTO.getName());
				job_Category.setDeleted(false);
				job_Category.setCreated_At(sqlDate);
				jobCategoryRepo.save(job_Category);
				Map<String, Object> error = new HashMap<>();
				error.put("success", "Thêm thành công !!!");
				return new ResponseEntity<>(error, HttpStatus.OK);
			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllBranch() {
		List<BranchDTO> branchDTO = new ArrayList<>();

		List<Branch> jobOptional = branchRepo.findAll().stream().toList();
		if (jobOptional.isEmpty()) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "Branch is empty");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		for (Branch job : jobOptional) {
			BranchDTO branchDTOElement = new BranchDTO();
			branchDTOElement.setId(job.getId());
			branchDTOElement.setName(job.getName());
			branchDTOElement.setAddress(job.getAddress());
			branchDTOElement.setImg(job.getImg());
			branchDTO.add(branchDTOElement);

		}
		return ResponseEntity.ok(branchDTO);
	}

	@Override
	public ResponseEntity<?> updateJobCategory(UpdateJobCategoryDTO jobDTO) {
		try {

			if (jobDTO.getName() == null || jobDTO.getName().trim() == "") {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Vui lòng không được bỏ trống trường tên !!!");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			} else {
				Optional<Job_Category> job_Category = jobCategoryRepo.findById(jobDTO.getId());
				if (job_Category.isPresent()) {
					if (jobDTO.getImage() == null) {
						Date date = new Date();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime());
						job_Category.get().setName(jobDTO.getName());
						job_Category.get().setUpdated_At(sqlDate);
						jobCategoryRepo.save(job_Category.get());
						Map<String, Object> success = new HashMap<>();
						success.put("success", "Cập nhập thành công !!!");
						return new ResponseEntity<>(success, HttpStatus.OK);
					}
					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					job_Category.get().setImage(jobDTO.getImage().getBytes());
					job_Category.get().setName(jobDTO.getName());
					job_Category.get().setUpdated_At(sqlDate);
					jobCategoryRepo.save(job_Category.get());
					Map<String, Object> success = new HashMap<>();
					success.put("success", "Thêm thành công !!!");
					return new ResponseEntity<>(success, HttpStatus.OK);
				} else {
					Map<String, Object> error = new HashMap<>();
					error.put("error", "Not exist !!!");
					return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
				}

			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteJobCategory(int jobId) {
		try {
			System.out.print("id" + jobId);
			Optional<Job_Category> job_Category = jobCategoryRepo.findById(jobId);
			if (job_Category.isPresent()) {
				job_Category.get().setDeleted(true);
				jobCategoryRepo.save(job_Category.get());
				Map<String, Object> success = new HashMap<>();
				success.put("success", "Xóa thành công !!!");
				return new ResponseEntity<>(success, HttpStatus.OK);
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Not exist !!!");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> createJob(JobDTO jobDTO) {
		System.out.print(jobDTO.toString());
		try {
			Job job = new Job();
			Optional<Branch> optionalBranch = branchRepo.findById(jobDTO.getBranch_Id());
			if (optionalBranch.isPresent() == false) {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Not found branch");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			Optional<Job_Category> jobCategoryOptional = jobCategoryRepo.findById(jobDTO.getCategory_Id());
			if (jobCategoryOptional.isEmpty()) {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Job category not found");
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			} else {
				job.setJob_category(jobCategoryOptional.get());
			}
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			job.setJobApplications(null);
			if (jobDTO.getCareer_Level().equals("Manager") || jobDTO.getCareer_Level().equals("Fresher")
					|| jobDTO.getCareer_Level().equals("Junior") || jobDTO.getCareer_Level().equals("Senior")) {
				job.setCareer_Level(jobDTO.getCareer_Level());
			} else {
				job.setCareer_Level("Others");
			}
			if (jobDTO.getExperience() >= 0) {
				job.setExperience(jobDTO.getExperience());
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Please input experience >= 0");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			if (jobDTO.getOffer_Salary().equals("0-$1000") || jobDTO.getOffer_Salary().equals("$1000-$2000")
					|| jobDTO.getOffer_Salary().equals("$2000-$3000") || jobDTO.getOffer_Salary().equals("$3000-$5000")
					|| jobDTO.getQualification().equals("$5000++")) {
				job.setOffer_Salary(jobDTO.getOffer_Salary());
			} else {
				job.setOffer_Salary("Negotiable");
			}
			if (jobDTO.getQualification().equals("Certificate") || jobDTO.getQualification().equals("Diploma")
					|| jobDTO.getQualification().equals("Associate Degree")
					|| jobDTO.getQualification().equals("Bachelor Degree")
					|| jobDTO.getQualification().equals("Master’s Degree")
					|| jobDTO.getQualification().equals("Doctorate Degree")) {
				job.setQualification(jobDTO.getQualification());
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Wrong input");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			job.setName(jobDTO.getName());
			job.setJob_Type(jobDTO.getJob_Type());
			job.setDescription(jobDTO.getDescription());
			job.setApply_Before(jobDTO.getApply_Before());
			job.setAddress(jobDTO.getAddress());
			job.setCreated_At(sqlDate);
			job.setUpdated_At(null);
			job.setBranch(optionalBranch.get());
			job.setDeleted(false);
			jobRepo.save(job);
			Map<String, String> success = new HashMap<String, String>();
			success.put("success", "Job created successfully !!!");
			return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> viewJob() {
		List<JobDTO> jobDTOS = new ArrayList<>();
		JobDTO jobDTO = new JobDTO();
		List<Job> jobOptional = jobRepo.findAll().stream().toList();
		if (jobOptional.isEmpty()) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "Job is empty");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		for (Job job : jobOptional) {
			if (job.isDeleted() == false) {
				jobDTO.setId(job.getId());
				jobDTO.setName(job.getName());
				jobDTO.setCategoryName(job.getJob_category().getName());
				jobDTO.setCareer_Level(job.getCareer_Level());
				jobDTO.setExperience(job.getExperience());
				jobDTO.setOffer_Salary(job.getOffer_Salary());
				jobDTO.setQualification(job.getQualification());
				jobDTO.setJob_Type(job.getJob_Type());
				jobDTO.setDescription(job.getDescription());
				jobDTO.setApply_Before(job.getApply_Before());
				jobDTO.setAddress(job.getAddress());
				jobDTO.setUpdate_At(job.getUpdated_At());
				jobDTO.setCreate_At(job.getCreated_At());

				LocalDate applyBeforeDate = job.getApply_Before().toLocalDate();

				// Check if the job is expired or valid based on applyBefore date
				if (LocalDate.now().isAfter(applyBeforeDate)) {
					jobDTO.setStatus("expired");
				} else {
					jobDTO.setStatus("valid");
				}

				jobDTOS.add(jobDTO);
			}
		}
		return ResponseEntity.ok(jobDTOS);
	}

	@Override
	public ResponseEntity<?> viewJobCategory() {
		List<JobCateDTO> jobCateDTOList = new ArrayList<>();
		List<Job_Category> jobOptional = jobCategoryRepo.findAll();
		if (jobOptional.isEmpty()) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "Job category is empty");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		for (Job_Category jobElement : jobOptional) {
			if (!jobElement.isDeleted()) {
				JobCateDTO job = new JobCateDTO(); // Create new instance inside the loop
				job.setId(jobElement.getId());
				job.setName(jobElement.getName());
				job.setImage(jobElement.getImage());
				job.setCreate_At(jobElement.getCreated_At());
				job.setUpdate_At(jobElement.getUpdated_At());
				jobCateDTOList.add(job);
			}
		}
		return ResponseEntity.ok(jobCateDTOList);
	}

	@Override
	public ResponseEntity<?> updateJob(JobDTO jobDTO) {
		try {
			Job job = new Job();
			Optional<Job> jobOptional = jobRepo.findById(jobDTO.getId());
			if (jobOptional.isEmpty()) {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Job not found");
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			} else {
				job = jobOptional.get();
			}

			Optional<Job_Category> jobCategoryOptional = jobCategoryRepo.findById(jobDTO.getCategory_Id());
			System.out.print(jobDTO.toString());
			if (jobCategoryOptional.isPresent()) {
				// Job category exists and is not null
				job.setJob_category(jobCategoryOptional.get());
			} else {
				// Job category not found or is null
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Job category not found");
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			}

			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			job.setJobApplications(null);
			if (jobDTO.getCareer_Level().equals("Manager") || jobDTO.getCareer_Level().equals("Fresher")
					|| jobDTO.getCareer_Level().equals("Junior") || jobDTO.getCareer_Level().equals("Senior")) {
				job.setCareer_Level(jobDTO.getCareer_Level());
			} else {
				job.setCareer_Level("Others");
			}
			if (jobDTO.getExperience() >= 0) {
				job.setExperience(jobDTO.getExperience());
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Please input experience >= 0");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			if (jobDTO.getOffer_Salary().equals("0-$1000") || jobDTO.getOffer_Salary().equals("$1000-$2000")
					|| jobDTO.getOffer_Salary().equals("$2000-$3000") || jobDTO.getOffer_Salary().equals("$3000-$5000")
					|| jobDTO.getQualification().equals("$5000++")) {
				job.setOffer_Salary(jobDTO.getOffer_Salary());
			} else {
				job.setOffer_Salary("Negotiable");
			}
			Optional<Branch> optionalBranch = branchRepo.findById(jobDTO.getBranch_Id());
			if (optionalBranch.isPresent() == false) {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Not found branch");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			} else {
				job.setBranch(optionalBranch.get());
			}
			if (jobDTO.getQualification().equals("Certificate") || jobDTO.getQualification().equals("Diploma")
					|| jobDTO.getQualification().equals("Associate Degree")
					|| jobDTO.getQualification().equals("Bachelor Degree")
					|| jobDTO.getQualification().equals("Master’s Degree")
					|| jobDTO.getQualification().equals("Doctorate Degree")) {
				job.setQualification(jobDTO.getQualification());
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Wrong input");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			job.setName(jobDTO.getName());
			job.setJob_Type(jobDTO.getJob_Type());
			job.setDescription(jobDTO.getDescription());
			job.setApply_Before(jobDTO.getApply_Before());
			job.setAddress(jobDTO.getAddress());
			job.setUpdated_At(sqlDate);
			jobRepo.save(job);
			Map<String, String> success = new HashMap<String, String>();
			success.put("success", "Job updated successfully !!!");
			return new ResponseEntity<>(success, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getJobInforById(int Id) {
		Job job = new Job();
		JobDTO jobDTO = new JobDTO();
		Optional<Job> jobOptional = jobRepo.findById(Id);
		if (jobOptional.isEmpty()) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "Job not found");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} else {
			job = jobOptional.get();
			jobDTO.setName(job.getName());
			jobDTO.setBranch_Id(job.getBranch().getId());
			jobDTO.setCategory_Id(job.getJob_category().getId());
			jobDTO.setCareer_Level(job.getCareer_Level());
			jobDTO.setExperience(job.getExperience());
			jobDTO.setOffer_Salary(job.getOffer_Salary());
			jobDTO.setQualification(job.getQualification());
			jobDTO.setJob_Type(job.getJob_Type());
			jobDTO.setDescription(job.getDescription());
			jobDTO.setApply_Before(job.getApply_Before());
			jobDTO.setAddress(job.getAddress());
			jobDTO.setJob_Type(job.getJob_Type());
			return ResponseEntity.ok(jobDTO);
		}
	}

	@Override
	public ResponseEntity<?> deleteJob(int jobId) {
		try {

			Optional<Job> job = jobRepo.findById(jobId);
			if (job.isPresent()) {
				job.get().setDeleted(true);
				jobRepo.save(job.get());
				Map<String, Object> success = new HashMap<>();
				success.put("success", "Xóa thành công !!!");
				return new ResponseEntity<>(success, HttpStatus.OK);
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("error", "Not exist !!!");
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			Map<String, Object> error = new HashMap<>();
			error.put("error", "INTERNAL SERVER ERROR !!!");
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
