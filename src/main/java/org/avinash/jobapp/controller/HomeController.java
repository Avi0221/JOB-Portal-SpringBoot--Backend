package org.avinash.jobapp.controller;

import org.avinash.jobapp.model.JobPost;
import org.avinash.jobapp.model.Role;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.service.JobApplicationService;
import org.avinash.jobapp.service.JobService;
import org.avinash.jobapp.service.SavedJobService;
import org.avinash.jobapp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService applicationService;

    @Autowired
    private SavedJobService savedJobService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String keyword, Model model, Authentication auth) {
        List<JobPost> jobs = (keyword != null && !keyword.isBlank())
                ? jobService.search(keyword)
                : jobService.getAllJobs();

        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("authenticated", auth != null && auth.isAuthenticated()
                && !(auth.getPrincipal() instanceof String));

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof org.avinash.jobapp.model.UserPrinciple) {
            User user = SecurityUtils.getCurrentUser();
            model.addAttribute("currentUser", user);
            if (user.getRole() == Role.JOBSEEKER) {
                model.addAttribute("appliedJobIds", applicationService.getApplicationsByJobSeeker(user).stream()
                        .map(a -> a.getJobPost().getPostId()).toList());
                model.addAttribute("savedJobIds", savedJobService.getSavedJobs(user).stream()
                        .map(s -> s.getJobPost().getPostId()).toList());
            }
        }

        return "home";
    }

    @GetMapping("/jobs/{id}")
    public String jobDetail(@PathVariable Long id, Model model, Authentication auth) {
        JobPost job = jobService.getJob(id);
        model.addAttribute("job", job);
        model.addAttribute("authenticated", auth != null && auth.isAuthenticated()
                && !(auth.getPrincipal() instanceof String));

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof org.avinash.jobapp.model.UserPrinciple) {
            User user = SecurityUtils.getCurrentUser();
            model.addAttribute("currentUser", user);
            if (user.getRole() == Role.JOBSEEKER) {
                model.addAttribute("hasApplied", applicationService.hasApplied(user, job));
                model.addAttribute("isSaved", savedJobService.isSaved(user, job));
            }
        }

        return "job-detail";
    }
}
