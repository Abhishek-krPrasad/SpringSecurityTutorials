package com.avi.UsingSpringSecurityDefault.service;

import com.avi.UsingSpringSecurityDefault.entity.Post;

public interface PostService {

    Post createPost(Long userId,Post post);

    Post getPostById(Long postID);
}
