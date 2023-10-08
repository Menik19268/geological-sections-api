package com.testing.geologicalsectionsapi.repositories;

import com.testing.geologicalsectionsapi.models.JobStatus;
import com.testing.geologicalsectionsapi.models.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    // Define any custom queries or methods related to JobStatus if needed
    Optional<JobStatus> findByIdAndJobType(Long id, JobType jobType);

}
