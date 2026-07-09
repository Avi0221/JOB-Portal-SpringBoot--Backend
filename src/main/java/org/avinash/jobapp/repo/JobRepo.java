package org.avinash.jobapp.repo;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<JobPost, Long> {
    List<JobPost> findByPostProfileContainingOrPostDescContaining(String postProfile, String postDesc);
    List<JobPost> findByEmployer(User employer);
    long countByEmployer(User employer);
}
