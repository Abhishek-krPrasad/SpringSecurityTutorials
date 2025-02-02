package com.avi.UsingSpringSecurityDefault.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private  Long id;
    private String accessToken;
    private String refreshToken;
}
