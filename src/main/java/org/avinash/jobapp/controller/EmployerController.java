package org.avinash.jobapp.controller;

import org.avinash.jobapp.model.ApplicationStatus;
import org.avinash.jobapp.model.JobApplication;
import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.JobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService applicationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User employer = SecurityUtils.getCurrentUser();
        List<JobPost> jobs = jobService.getJobsByEmployer(employer);
        List<JobApplication> applications = applicationService.getApplicationsForEmployer(employer);

        model.addAttribute("employer", employer);
        model.addAttribute("jobs", jobs);
        model.addAttribute("applications", applications.stream().limit(5).toList());
        model.addAttribute("totalJobs", jobService.countByEmployer(employer));
        model.addAttribute("totalApplications", applicationService.countByEmployer(employer));
        return "employer/dashboard";
    }

    @GetMapping("/jobs/new")
    public String newJobForm(Model model) {
        model.addAttribute("job", new JobPost());
        return "employer/job-form";
    }

    @PostMapping("/jobs")
    public String createJob(@ModelAttribute JobPost job,
                            @RequestParam(required = false) String techStack,
                            RedirectAttributes redirectAttributes) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            job.setPostTechStack(parseTechStack(techStack));
            jobService.addJob(job, employer);
            redirectAttributes.addFlashAttribute("success", "Job posted successfully!");
            return "redirect:/employer/dashboard";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/employer/jobs/new";
        }
    }

    @GetMapping("/jobs/{id}/edit")
    public String editJobForm(@PathVariable Long id, Model model) {
        User employer = SecurityUtils.getCurrentUser();
        JobPost job = jobService.getJob(id);
        if (job.getEmployer() == null || !job.getEmployer().getId().equals(employer.getId())) {
            return "redirect:/employer/dashboard";
        }
        model.addAttribute("job", job);
        model.addAttribute("techStackValue", String.join(", ", job.getPostTechStack()));
        return "employer/job-form";
    }

    @PostMapping("/jobs/{id}/edit")
    public String updateJob(@PathVariable Long id,
                            @ModelAttribute JobPost job,
                            @RequestParam(required = false) String techStack,
                            RedirectAttributes redirectAttributes) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            job.setPostId(id);
            job.setPostTechStack(parseTechStack(techStack));
            jobService.updateJob(job, employer);
            redirectAttributes.addFlashAttribute("success", "Job updated successfully!");
            return "redirect:/employer/dashboard";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/employer/jobs/" + id + "/edit";
        }
    }

    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            jobService.deleteJob(id, employer);
            redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        User employer = SecurityUtils.getCurrentUser();
        model.addAttribute("applications", applicationService.getApplicationsForEmployer(employer));
        model.addAttribute("statuses", ApplicationStatus.values());
        return "employer/applications";
    }

    @PostMapping("/applications/{id}/status")
    public String updateApplicationStatus(@PathVariable Long id,
                                          @RequestParam ApplicationStatus status,
                                          RedirectAttributes redirectAttributes) {
        try {
            User employer = SecurityUtils.getCurrentUser();
            applicationService.updateStatus(id, status, employer);
            redirectAttributes.addFlashAttribute("success", "Application status updated!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/employer/applications";
    }

    private List<String> parseTechStack(String techStack) {
        if (techStack == null || techStack.isBlank()) {
            return List.of();
        }
        return Arrays.stream(techStack.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
