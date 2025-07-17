package com.blogapp.api.service;

import com.blogapp.api.dto.LoginDto;
import com.blogapp.api.dto.UserDto;

public interface AuthService {
    String registerUser(UserDto userDto);
    String verifyUser(LoginDto loginDto);
}
