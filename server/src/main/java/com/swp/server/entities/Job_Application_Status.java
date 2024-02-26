package com.swp.server.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Job_Application_Status")
public class Job_Application_Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @OneToMany(mappedBy = "status" , cascade = CascadeType.ALL)
    private List<Job_Application> jobApplications;
    @Column(name = "Name")
    private String Name;
    @Column(name = "Created_At")
    private Date Created_At;
    @Column(name = "Updated_At")
    private Date Updated_At;

    public Job_Application_Status() {
    }

    public Job_Application_Status(int id, List<Job_Application> jobApplications, String name, Date created_At, Date updated_At) {
        Id = id;
        this.jobApplications = jobApplications;
        Name = name;
        Created_At = created_At;
        Updated_At = updated_At;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<Job_Application> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<Job_Application> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getCreated_At() {
        return Created_At;
    }

    public void setCreated_At(Date created_At) {
        Created_At = created_At;
    }

    public Date getUpdated_At() {
        return Updated_At;
    }

    public void setUpdated_At(Date updated_At) {
        Updated_At = updated_At;
    }
}
