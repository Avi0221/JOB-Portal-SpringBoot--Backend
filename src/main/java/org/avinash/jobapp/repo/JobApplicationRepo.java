package org.avinash.jobapp.repo;

import org.avinash.jobapp.model.JobApplication;
import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobSeekerOrderByAppliedAtDesc(User jobSeeker);
    List<JobApplication> findByJobPostEmployerOrderByAppliedAtDesc(User employer);
    List<JobApplication> findByJobPostOrderByAppliedAtDesc(JobPost jobPost);
    Optional<JobApplication> findByJobSeekerAndJobPost(User jobSeeker, JobPost jobPost);
    boolean existsByJobSeekerAndJobPost(User jobSeeker, JobPost jobPost);
    long countByJobSeeker(User jobSeeker);
    long countByJobPostEmployer(User employer);
}
