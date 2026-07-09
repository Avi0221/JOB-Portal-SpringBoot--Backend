package org.avinash.jobapp.service;


import org.avinash.jobapp.model.User;
import org.avinash.jobapp.model.UserPrinciple;
import org.avinash.jobapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);

        if(user==null){
            System.out.println("User NOT FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return new UserPrinciple(user);
    }
}
