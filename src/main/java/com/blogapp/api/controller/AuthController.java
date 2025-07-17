package com.blogapp.api.controller;

import com.blogapp.api.dto.JwtAuthResponse;
import com.blogapp.api.dto.LoginDto;
import com.blogapp.api.dto.UserDto;
import com.blogapp.api.service.AuthService;
import com.blogapp.api.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User login and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.registerUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.verifyUser(loginDto);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }


}
