package org.avinash.jobapp.controller;

import org.avinash.jobapp.dto.ApiDtos.ApplicationResponse;
import org.avinash.jobapp.dto.ApiDtos.ApplyRequest;
import org.avinash.jobapp.dto.ApiDtos.ErrorResponse;
import org.avinash.jobapp.dto.ApiDtos.JobSeekerDashboardResponse;
import org.avinash.jobapp.dto.ApiDtos.SavedJobResponse;
import org.avinash.jobapp.dto.ApiDtos.UserResponse;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.SavedJobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobseeker")
@PreAuthorize("hasRole('JOBSEEKER')")
public class ApiJobSeekerController {

    @Autowired
    private JobApplicationService applicationService;

    @Autowired
    private SavedJobService savedJobService;

    @GetMapping("/dashboard")
    public JobSeekerDashboardResponse dashboard() {
        User jobSeeker = SecurityUtils.getCurrentUser();
        List<ApplicationResponse> applications = applicationService.getApplicationsByJobSeeker(jobSeeker)
                .stream().limit(5).map(ApplicationResponse::from).toList();
        List<SavedJobResponse> savedJobs = savedJobService.getSavedJobs(jobSeeker)
                .stream().limit(5).map(SavedJobResponse::from).toList();

        return new JobSeekerDashboardResponse(
                UserResponse.from(jobSeeker),
                applications,
                savedJobs,
                applicationService.countByJobSeeker(jobSeeker),
                savedJobService.countByJobSeeker(jobSeeker)
        );
    }

    @GetMapping("/applications")
    public List<ApplicationResponse> applications() {
        User jobSeeker = SecurityUtils.getCurrentUser();
        return applicationService.getApplicationsByJobSeeker(jobSeeker)
                .stream().map(ApplicationResponse::from).toList();
    }

    @PostMapping("/jobs/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Long id, @RequestBody(required = false) ApplyRequest request) {
        try {
            User jobSeeker = SecurityUtils.getCurrentUser();
            String coverNote = request == null ? null : request.coverNote();
            return ResponseEntity.ok(ApplicationResponse.from(applicationService.apply(jobSeeker, id, coverNote)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/saved")
    public List<SavedJobResponse> savedJobs() {
        User jobSeeker = SecurityUtils.getCurrentUser();
        return savedJobService.getSavedJobs(jobSeeker).stream().map(SavedJobResponse::from).toList();
    }

    @PostMapping("/jobs/{id}/save")
    public ResponseEntity<?> saveJob(@PathVariable Long id) {
        try {
            User jobSeeker = SecurityUtils.getCurrentUser();
            return ResponseEntity.ok(SavedJobResponse.from(savedJobService.saveJob(jobSeeker, id)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/jobs/{id}/save")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long id) {
        User jobSeeker = SecurityUtils.getCurrentUser();
        savedJobService.unsaveJob(jobSeeker, id);
        return ResponseEntity.noContent().build();
    }
}
