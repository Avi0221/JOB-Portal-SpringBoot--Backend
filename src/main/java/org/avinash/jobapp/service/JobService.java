package org.avinash.jobapp.service;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    public JobPost addJob(JobPost jobPost, User employer) {
        jobPost.setEmployer(employer);
        if (jobPost.getCompany() == null || jobPost.getCompany().isBlank()) {
            jobPost.setCompany(employer.getCompanyName());
        }
        return repo.save(jobPost);
    }

    public List<JobPost> getAllJobs() {
        return repo.findAll();
    }

    public JobPost getJob(Long postId) {
        return repo.findById(postId).orElseThrow(() -> new IllegalArgumentException("Job not found"));
    }

    public JobPost updateJob(JobPost jobPost, User employer) {
        JobPost existing = getJob(jobPost.getPostId());
        if (existing.getEmployer() == null || !existing.getEmployer().getId().equals(employer.getId())) {
            throw new IllegalArgumentException("You can only edit your own job postings");
        }
        existing.setPostProfile(jobPost.getPostProfile());
        existing.setPostDesc(jobPost.getPostDesc());
        existing.setReqExperience(jobPost.getReqExperience());
        existing.setPostTechStack(jobPost.getPostTechStack());
        existing.setLocation(jobPost.getLocation());
        existing.setCompany(jobPost.getCompany());
        return repo.save(existing);
    }

    public void deleteJob(Long postId, User employer) {
        JobPost job = getJob(postId);
        if (job.getEmployer() == null || !job.getEmployer().getId().equals(employer.getId())) {
            throw new IllegalArgumentException("You can only delete your own job postings");
        }
        repo.deleteById(postId);
    }

    public List<JobPost> getJobsByEmployer(User employer) {
        return repo.findByEmployer(employer);
    }

    public List<JobPost> search(String keyword) {
        return repo.findByPostProfileContainingOrPostDescContaining(keyword, keyword);
    }

    public long countByEmployer(User employer) {
        return repo.countByEmployer(employer);
    }

    public void loadSampleData() {
        if (repo.count() > 0) {
            return;
        }

        List<JobPost> jobs = Arrays.asList(
                createSampleJob("Java Developer", "Must have good experience in core Java and advanced Java", 2,
                        List.of("Core Java", "J2EE", "Spring Boot", "Hibernate"), "Bangalore", "TechCorp"),
                createSampleJob("Frontend Developer", "Experience in building responsive web applications using React", 3,
                        List.of("HTML", "CSS", "JavaScript", "React"), "Remote", "WebWorks"),
                createSampleJob("Data Scientist", "Strong background in machine learning and data analysis", 4,
                        List.of("Python", "Machine Learning", "Data Analysis"), "Hyderabad", "DataLabs"),
                createSampleJob("Network Engineer", "Design and implement computer networks for efficient data communication", 5,
                        List.of("Networking", "Cisco", "Routing", "Switching"), "Chennai", "NetSys"),
                createSampleJob("Mobile App Developer", "Experience in mobile app development for iOS and Android", 3,
                        List.of("iOS Development", "Android Development", "Mobile App"), "Pune", "AppStudio")
        );
        repo.saveAll(jobs);
    }

    private JobPost createSampleJob(String profile, String desc, int exp, List<String> stack, String location, String company) {
        JobPost job = new JobPost();
        job.setPostProfile(profile);
        job.setPostDesc(desc);
        job.setReqExperience(exp);
        job.setPostTechStack(stack);
        job.setLocation(location);
        job.setCompany(company);
        return job;
    }
}
