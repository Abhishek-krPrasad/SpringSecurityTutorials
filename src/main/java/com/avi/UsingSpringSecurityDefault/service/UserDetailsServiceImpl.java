package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.LoginDTO;
import com.avi.UsingSpringSecurityDefault.entity.SignUpDTO;
import com.avi.UsingSpringSecurityDefault.entity.User;
import com.avi.UsingSpringSecurityDefault.entity.UserDTO;
import com.avi.UsingSpringSecurityDefault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(username).orElseThrow(()-> new BadCredentialsException("User with email : "+ username +" not found"));
    }

    public User getUserByUserId(Long userId){
       return userRepository.findById(userId).orElseThrow();
    }
}


