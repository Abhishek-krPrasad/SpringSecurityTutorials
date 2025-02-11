package com.avi.UsingSpringSecurityDefault.controller;

import com.avi.UsingSpringSecurityDefault.entity.LoginDTO;
import com.avi.UsingSpringSecurityDefault.entity.LoginResponse;
import com.avi.UsingSpringSecurityDefault.entity.SignUpDTO;
import com.avi.UsingSpringSecurityDefault.entity.UserDTO;
import com.avi.UsingSpringSecurityDefault.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> registerUser(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.ok(authService.registerUser(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO,HttpServletResponse response){

        LoginResponse loginResponse = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request, HttpServletResponse response){
            String refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equalsIgnoreCase("refreshToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseThrow(()-> new AuthenticationServiceException("No refresh Token found"));

            LoginResponse loginResponse = authService.refresh(refreshToken);

            return ResponseEntity.ok(loginResponse);
    }


}
