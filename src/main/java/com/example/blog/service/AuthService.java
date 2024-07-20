package com.example.blog.service;

import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
