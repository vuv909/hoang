package com.swp.server.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Job_Application")
public class Job_Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    @ManyToOne
    @JoinColumn(name = "Account_Id", referencedColumnName = "Id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "Job_Id", referencedColumnName = "Id")
    private Job job;
    @ManyToOne
    @JoinColumn(name = "Status_Id", referencedColumnName = "Id")
    private Job_Application_Status status;
    @Column(name = "Full_Name")
    private String Full_Name;
    @Column(name = "Email")
    private String Email;
    @Column(name = "Phone_Number")
    private String Phone_Number;
    @Column(name = "CV")
    private String CV;
    @Column(name = "Cover_Letter")
    private String Cover_Letter;
    @Column(name = "Created_At")
    private Date Created_At;
    @Column(name = "Updated_At")
    private Date Updated_At;

    public Job_Application() {
    }

    public Job_Application(int id, Account account, Job job, Job_Application_Status status, String full_Name, String email, String phone_Number, String CV, String cover_Letter, Date created_At, Date updated_At) {
        Id = id;
        this.account = account;
        this.job = job;
        this.status = status;
        Full_Name = full_Name;
        Email = email;
        Phone_Number = phone_Number;
        this.CV = CV;
        Cover_Letter = cover_Letter;
        Created_At = created_At;
        Updated_At = updated_At;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job_Application_Status getStatus() {
        return status;
    }

    public void setStatus(Job_Application_Status status) {
        this.status = status;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }

    public String getCover_Letter() {
        return Cover_Letter;
    }

    public void setCover_Letter(String cover_Letter) {
        Cover_Letter = cover_Letter;
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
