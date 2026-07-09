package org.avinash.jobapp.service;

import org.avinash.jobapp.model.ApplicationStatus;
import org.avinash.jobapp.model.JobApplication;
import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.repo.JobApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepo repo;

    @Autowired
    private JobService jobService;

    public JobApplication apply(User jobSeeker, Long jobPostId, String coverNote) {
        JobPost jobPost = jobService.getJob(jobPostId);
        if (repo.existsByJobSeekerAndJobPost(jobSeeker, jobPost)) {
            throw new IllegalArgumentException("You have already applied for this job");
        }

        JobApplication application = new JobApplication();
        application.setJobSeeker(jobSeeker);
        application.setJobPost(jobPost);
        application.setCoverNote(coverNote);
        application.setStatus(ApplicationStatus.APPLIED);
        return repo.save(application);
    }

    public List<JobApplication> getApplicationsByJobSeeker(User jobSeeker) {
        return repo.findByJobSeekerOrderByAppliedAtDesc(jobSeeker);
    }

    public List<JobApplication> getApplicationsForEmployer(User employer) {
        return repo.findByJobPostEmployerOrderByAppliedAtDesc(employer);
    }

    public List<JobApplication> getApplicationsForJob(Long jobPostId, User employer) {
        JobPost jobPost = jobService.getJob(jobPostId);
        if (jobPost.getEmployer() == null || !jobPost.getEmployer().getId().equals(employer.getId())) {
            throw new IllegalArgumentException("You can only view applications for your own jobs");
        }
        return repo.findByJobPostOrderByAppliedAtDesc(jobPost);
    }

    public JobApplication updateStatus(Long applicationId, ApplicationStatus status, User employer) {
        JobApplication application = repo.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (application.getJobPost().getEmployer() == null
                || !application.getJobPost().getEmployer().getId().equals(employer.getId())) {
            throw new IllegalArgumentException("You can only update applications for your own jobs");
        }

        application.setStatus(status);
        return repo.save(application);
    }

    public boolean hasApplied(User jobSeeker, JobPost jobPost) {
        return repo.existsByJobSeekerAndJobPost(jobSeeker, jobPost);
    }

    public long countByJobSeeker(User jobSeeker) {
        return repo.countByJobSeeker(jobSeeker);
    }

    public long countByEmployer(User employer) {
        return repo.countByJobPostEmployer(employer);
    }
}
