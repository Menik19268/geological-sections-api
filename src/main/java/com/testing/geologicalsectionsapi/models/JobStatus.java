package com.testing.geologicalsectionsapi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "JOB_STATUSES")
@Data
public class JobStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private JobStatusEnum status;

    private String result;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "file_path")
    private String filePath;

    public JobStatus() {
    }

    public JobStatus(JobType jobType, JobStatusEnum status) {
        this.jobType = jobType;
        this.status = status;
    }


}

