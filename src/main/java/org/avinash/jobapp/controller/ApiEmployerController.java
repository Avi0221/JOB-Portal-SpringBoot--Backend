package org.avinash.jobapp.controller;

import org.avinash.jobapp.dto.ApiDtos.ApplicationResponse;
import org.avinash.jobapp.dto.ApiDtos.EmployerDashboardResponse;
import org.avinash.jobapp.dto.ApiDtos.ErrorResponse;
import org.avinash.jobapp.dto.ApiDtos.JobResponse;
import org.avinash.jobapp.dto.ApiDtos.StatusRequest;
import org.avinash.jobapp.dto.ApiDtos.UserResponse;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.JobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
@PreAuthorize("hasRole('EMPLOYER')")
public class ApiEmployerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService applicationService;

    @GetMapping("/dashboard")
    public EmployerDashboardResponse dashboard() {
        User employer = SecurityUtils.getCurrentUser();
        List<JobResponse> jobs = jobService.getJobsByEmployer(employer).stream().map(JobResponse::from).toList();
        List<ApplicationResponse> applications = applicationService.getApplicationsForEmployer(employer)
                .stream().limit(5).map(ApplicationResponse::from).toList();

        return new EmployerDashboardResponse(
                UserResponse.from(employer),
                jobs,
                applications,
                jobService.countByEmployer(employer),
                applicationService.countByEmployer(employer)
        );
    }

    @GetMapping("/applications")
    public List<ApplicationResponse> applications() {
        User employer = SecurityUtils.getCurrentUser();
        return applicationService.getApplicationsForEmployer(employer).stream().map(ApplicationResponse::from).toList();
    }

    @PatchMapping("/applications/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            return ResponseEntity.ok(ApplicationResponse.from(
                    applicationService.updateStatus(id, request.status(), employer)
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }
    }
}
