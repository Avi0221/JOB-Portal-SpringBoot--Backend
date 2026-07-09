package org.avinash.jobapp.controller;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class JobRestController {

    @Autowired
    private JobService service;

    @GetMapping("jobPosts")
    public List<JobPost> getAllJobs() {
        return service.getAllJobs();
    }

    @GetMapping("jobPost/{postId}")
    public JobPost getJob(@PathVariable("postId") Long postId) {
        return service.getJob(postId);
    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost> searchByKeyword(@PathVariable("keyword") String keyword) {
        return service.search(keyword);
    }

    @PostMapping("jobPost")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobPost addJob(@RequestBody JobPost jobPost) {
        User employer = SecurityUtils.getCurrentUser();
        return service.addJob(jobPost, employer);
    }

    @PutMapping("jobPost")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobPost updateJob(@RequestBody JobPost jobPost) {
        User employer = SecurityUtils.getCurrentUser();
        return service.updateJob(jobPost, employer);
    }

    @DeleteMapping("jobPost/{postId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public String deleteJob(@PathVariable Long postId) {
        User employer = SecurityUtils.getCurrentUser();
        service.deleteJob(postId, employer);
        return "Deleted";
    }

    @GetMapping("load")
    public String loadData() {
        service.loadSampleData();
        return "success";
    }
}
