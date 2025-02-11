package com.avi.UsingSpringSecurityDefault.repository;

import com.avi.UsingSpringSecurityDefault.entity.Session;
import com.avi.UsingSpringSecurityDefault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
