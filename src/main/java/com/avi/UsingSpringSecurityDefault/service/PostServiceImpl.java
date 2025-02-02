package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.Post;
import com.avi.UsingSpringSecurityDefault.entity.User;
import com.avi.UsingSpringSecurityDefault.repository.PostRepository;
import com.avi.UsingSpringSecurityDefault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public Post createPost(Long userId, Post post) {
        User user = userRepository.findById(userId).orElseThrow();
        post.setUser(user);
        return postRepository.save(post);

    }

    @Override
    public Post getPostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow();
        return post;
    }
}
