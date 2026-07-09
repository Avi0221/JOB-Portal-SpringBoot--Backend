package org.avinash.jobapp.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.avinash.jobapp.model.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
        String redirectUrl = switch (principal.getUser().getRole()) {
            case EMPLOYER -> "/employer/dashboard";
            case JOBSEEKER -> "/jobseeker/dashboard";
        };
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
