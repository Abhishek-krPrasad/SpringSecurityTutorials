package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.LoginDTO;
import com.avi.UsingSpringSecurityDefault.entity.LoginResponse;
import com.avi.UsingSpringSecurityDefault.entity.SignUpDTO;
import com.avi.UsingSpringSecurityDefault.entity.UserDTO;


public interface AuthService {
    public UserDTO registerUser(SignUpDTO signUpDTO);

    public LoginResponse login(LoginDTO loginDTO);


    public LoginResponse refresh(String refreshToken);
}
