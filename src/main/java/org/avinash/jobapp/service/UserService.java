package org.avinash.jobapp.service;

import org.avinash.jobapp.dto.RegistrationDto;
import org.avinash.jobapp.model.Role;
import org.avinash.jobapp.model.User;
import org.avinash.jobapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User register(RegistrationDto dto) {
        if (repo.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (dto.getRole() == Role.EMPLOYER && (dto.getCompanyName() == null || dto.getCompanyName().isBlank())) {
            throw new IllegalArgumentException("Company name is required for employers");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        user.setCompanyName(dto.getRole() == Role.EMPLOYER ? dto.getCompanyName() : null);
        return saveUser(user);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
