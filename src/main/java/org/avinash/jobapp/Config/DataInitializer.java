package org.avinash.jobapp.Config;

import org.avinash.jobapp.model.Role;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.repo.UserRepo;
import org.avinash.jobapp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(String... args) {
        migrateLegacyUsers();
        jobService.loadSampleData();
    }

    private void migrateLegacyUsers() {
        for (User user : userRepo.findAll()) {
            boolean changed = false;
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                user.setEmail(user.getUsername() + "@legacy.local");
                changed = true;
            }
            if (user.getFullName() == null || user.getFullName().isBlank()) {
                user.setFullName(user.getUsername());
                changed = true;
            }
            if (user.getRole() == null) {
                user.setRole(Role.JOBSEEKER);
                changed = true;
            }
            if (changed) {
                userRepo.save(user);
            }
        }
    }
}
