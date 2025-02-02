package com.avi.UsingSpringSecurityDefault.repository;

import com.avi.UsingSpringSecurityDefault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String username);
}
