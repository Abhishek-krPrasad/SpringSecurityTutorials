package com.avi.UsingSpringSecurityDefault.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class UserDTO {

    private Long id;

    private String email;

    private Set<Post> posts;


}
