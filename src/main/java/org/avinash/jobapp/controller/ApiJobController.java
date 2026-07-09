package org.avinash.jobapp.controller;

import org.avinash.jobapp.dto.ApiDtos.ErrorResponse;
import org.avinash.jobapp.dto.ApiDtos.JobDetailResponse;
import org.avinash.jobapp.dto.ApiDtos.JobRequest;
import org.avinash.jobapp.dto.ApiDtos.JobResponse;
import org.avinash.jobapp.dto.ApiDtos.UserResponse;
import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.Role;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.model.UserPrinciple;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.JobService;
import org.avinash.jobapp.service.SavedJobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class ApiJobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService applicationService;

    @Autowired
    private SavedJobService savedJobService;

    @GetMapping
    public List<JobResponse> jobs(@RequestParam(required = false) String keyword) {
        List<JobPost> jobs = keyword != null && !keyword.isBlank()
                ? jobService.search(keyword)
                : jobService.getAllJobs();
        return jobs.stream().map(JobResponse::from).toList();
    }

    @GetMapping("/{id}")
    public JobDetailResponse job(@PathVariable Long id, Authentication authentication) {
        JobPost job = jobService.getJob(id);
        boolean authenticated = authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserPrinciple;
        User currentUser = authenticated ? SecurityUtils.getCurrentUser() : null;
        boolean hasApplied = false;
        boolean saved = false;

        if (currentUser != null && currentUser.getRole() == Role.JOBSEEKER) {
            hasApplied = applicationService.hasApplied(currentUser, job);
            saved = savedJobService.isSaved(currentUser, job);
        }

        return new JobDetailResponse(
                JobResponse.from(job),
                authenticated,
                UserResponse.from(currentUser),
                hasApplied,
                saved
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponse createJob(@RequestBody JobRequest request) {
        User employer = SecurityUtils.getCurrentUser();
        return JobResponse.from(jobService.addJob(request.toJobPost(), employer));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponse updateJob(@PathVariable Long id, @RequestBody JobRequest request) {
        User employer = SecurityUtils.getCurrentUser();
        JobPost job = request.toJobPost();
        job.setPostId(id);
        return JobResponse.from(jobService.updateJob(job, employer));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            jobService.deleteJob(id, employer);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }
}
