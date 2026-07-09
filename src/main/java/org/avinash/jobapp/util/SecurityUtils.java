package org.avinash.jobapp.util;

import org.avinash.jobapp.model.User;
import org.avinash.jobapp.model.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple userPrinciple) {
            return userPrinciple.getUser();
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
