package com.avi.UsingSpringSecurityDefault.controller;

import com.avi.UsingSpringSecurityDefault.entity.Post;
import com.avi.UsingSpringSecurityDefault.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final PostService postService;




    @PostMapping("/{id}/post")
    public ResponseEntity<Post> createPost(@PathVariable Long id,@RequestBody Post post){
        return ResponseEntity.ok(postService.createPost(id,post));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }




}
