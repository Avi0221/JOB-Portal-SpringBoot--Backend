package org.avinash.jobapp.service;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.SavedJob;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.repo.SavedJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SavedJobRepo repo;

    @Autowired
    private JobService jobService;

    public SavedJob saveJob(User jobSeeker, Long jobPostId) {
        JobPost jobPost = jobService.getJob(jobPostId);
        if (repo.existsByJobSeekerAndJobPost(jobSeeker, jobPost)) {
            throw new IllegalArgumentException("Job already saved");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setJobSeeker(jobSeeker);
        savedJob.setJobPost(jobPost);
        return repo.save(savedJob);
    }

    public void unsaveJob(User jobSeeker, Long jobPostId) {
        JobPost jobPost = jobService.getJob(jobPostId);
        repo.deleteByJobSeekerAndJobPost(jobSeeker, jobPost);
    }

    public List<SavedJob> getSavedJobs(User jobSeeker) {
        return repo.findByJobSeekerOrderBySavedAtDesc(jobSeeker);
    }

    public boolean isSaved(User jobSeeker, JobPost jobPost) {
        return repo.existsByJobSeekerAndJobPost(jobSeeker, jobPost);
    }

    public long countByJobSeeker(User jobSeeker) {
        return repo.countByJobSeeker(jobSeeker);
    }
}
