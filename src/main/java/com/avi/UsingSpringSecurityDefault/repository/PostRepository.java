package com.avi.UsingSpringSecurityDefault.repository;

import com.avi.UsingSpringSecurityDefault.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
