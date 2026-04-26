package com.siddhesh.aiassessmentagent.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String jobDescription;

    @Column(length = 5000)
    private String resume;

    @Column(length = 10000)
    private String result;

    private LocalDateTime createdAt;


    public Assessment() {}

    public Assessment(String jobDescription, String resume, String result) {
        this.jobDescription = jobDescription;
        this.resume = resume;
        this.result = result;
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() { return id; }

    public String getJobDescription() { return jobDescription; }

    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public String getResume() { return resume; }

    public void setResume(String resume) { this.resume = resume; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}