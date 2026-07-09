package org.avinash.jobapp.controller;

import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.SavedJobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jobseeker")
public class JobSeekerController {

    @Autowired
    private JobApplicationService applicationService;

    @Autowired
    private SavedJobService savedJobService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User jobSeeker = SecurityUtils.getCurrentUser();
        model.addAttribute("jobSeeker", jobSeeker);
        model.addAttribute("applications", applicationService.getApplicationsByJobSeeker(jobSeeker).stream().limit(5).toList());
        model.addAttribute("savedJobs", savedJobService.getSavedJobs(jobSeeker).stream().limit(5).toList());
        model.addAttribute("totalApplications", applicationService.countByJobSeeker(jobSeeker));
        model.addAttribute("totalSaved", savedJobService.countByJobSeeker(jobSeeker));
        return "jobseeker/dashboard";
    }

    @PostMapping("/jobs/{id}/apply")
    public String apply(@PathVariable Long id,
                        @RequestParam(required = false) String coverNote,
                        RedirectAttributes redirectAttributes) {
        try {
            User jobSeeker = SecurityUtils.getCurrentUser();
            applicationService.apply(jobSeeker, id, coverNote);
            redirectAttributes.addFlashAttribute("success", "Application submitted successfully!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/jobs/" + id;
    }

    @PostMapping("/jobs/{id}/save")
    public String saveJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User jobSeeker = SecurityUtils.getCurrentUser();
            savedJobService.saveJob(jobSeeker, id);
            redirectAttributes.addFlashAttribute("success", "Job saved to your list!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/jobs/" + id;
    }

    @PostMapping("/jobs/{id}/unsave")
    public String unsaveJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User jobSeeker = SecurityUtils.getCurrentUser();
        savedJobService.unsaveJob(jobSeeker, id);
        redirectAttributes.addFlashAttribute("success", "Job removed from saved list.");
        return "redirect:/jobs/" + id;
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        User jobSeeker = SecurityUtils.getCurrentUser();
        model.addAttribute("applications", applicationService.getApplicationsByJobSeeker(jobSeeker));
        return "jobseeker/applications";
    }

    @GetMapping("/saved")
    public String savedJobs(Model model) {
        User jobSeeker = SecurityUtils.getCurrentUser();
        model.addAttribute("savedJobs", savedJobService.getSavedJobs(jobSeeker));
        return "jobseeker/saved";
    }
}
