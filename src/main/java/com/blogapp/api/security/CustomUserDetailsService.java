package com.blogapp.api.security;

import com.blogapp.api.entity.User;
import com.blogapp.api.exceptions.ResourceNotFoundException;
import com.blogapp.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found with username or email: " + usernameOrEmail));
        return new UserPrinciple(user);
    }
}
