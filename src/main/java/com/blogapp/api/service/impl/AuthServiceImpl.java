package com.blogapp.api.service.impl;

import com.blogapp.api.dto.LoginDto;
import com.blogapp.api.dto.UserDto;
import com.blogapp.api.entity.Role;
import com.blogapp.api.entity.User;
import com.blogapp.api.exceptions.ResourceNotFoundException;
import com.blogapp.api.repository.RoleRepository;
import com.blogapp.api.repository.UserRepository;
import com.blogapp.api.security.JwtTokenProvider;
import com.blogapp.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public String registerUser(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            throw new ResourceNotFoundException("Username already exists");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new ResourceNotFoundException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(()->new ResourceNotFoundException("Default role not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return "user registered successfully";
    }

    @Override
    public String verifyUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Extract UserDetails from authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtTokenProvider.generateToken(authentication.getName(), userDetails);

    }
}
