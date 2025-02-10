package com.avi.UsingSpringSecurityDefault.handler;

import com.avi.UsingSpringSecurityDefault.entity.User;
import com.avi.UsingSpringSecurityDefault.service.JwtService;
import com.avi.UsingSpringSecurityDefault.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private  final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        // Log OAuth2 user details
        log.info("User Attributes: {}", oAuth2User.getAttributes());

        // Get user email from OAuth2 response
        String email = oAuth2User.getAttribute("email");

        if (email == null) {
            log.error("OAuth2 authentication successful, but email is null!");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in OAuth2 response");
            return;
        }

        // Check if user exists in database
        log.info("Checking user existence for email: {}", email);
        User user = userDetailsService.getUserByUsername(email);
        log.info("User fetched from DB: {}", user);


        if (user == null) {
            log.info("New user detected, creating entry in database...");

            User newUser = User.builder()
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .build();

            user = userDetailsService.createUser(newUser);
        }

        // Generate JWT tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Set refresh token in a HttpOnly cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/"); // Cookie accessible from all routes
        response.addCookie(refreshTokenCookie);

        // Include accessToken in response headers
        response.setHeader("Authorization", "Bearer " + accessToken);

        // Redirect to frontend
        String frontEndUrl = "http://localhost:8080/home.html"; // Change this based on your frontend setup
        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
    }
}
