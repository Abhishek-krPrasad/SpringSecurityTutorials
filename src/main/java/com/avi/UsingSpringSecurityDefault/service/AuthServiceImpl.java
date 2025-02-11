package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.*;
import com.avi.UsingSpringSecurityDefault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final SessionService sessionService;

    public UserDTO registerUser(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findUserByEmail(signUpDTO.getEmail());

        if (user.isPresent()){
            throw  new BadCredentialsException("User Already exist with this email");
        }

        User toCreate = modelMapper.map(signUpDTO,User.class);
        toCreate.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        User savedUser = userRepository.save(toCreate);

        return modelMapper.map(savedUser,UserDTO.class);

    }
    @Override
    public LoginResponse login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user,refreshToken);


        return LoginResponse.builder()
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponse refresh(String refreshToken) {

        Long userId = jwtService.getUserIdFromToken(refreshToken);

        sessionService.validateSession(refreshToken);

        User user = userDetailsService.getUserByUserId(userId);

        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .id(userId)
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}

