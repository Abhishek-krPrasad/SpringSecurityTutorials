package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.User;

public interface SessionService {

    public void generateNewSession(User user, String refreshToken);
    public void validateSession(String refreshToken);

}
