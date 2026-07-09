package org.avinash.jobapp.repo;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.SavedJob;
import org.avinash.jobapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepo extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByJobSeekerOrderBySavedAtDesc(User jobSeeker);
    Optional<SavedJob> findByJobSeekerAndJobPost(User jobSeeker, JobPost jobPost);
    boolean existsByJobSeekerAndJobPost(User jobSeeker, JobPost jobPost);
    void deleteByJobSeekerAndJobPost(User jobSeeker, JobPost jobPost);
    long countByJobSeeker(User jobSeeker);
}
