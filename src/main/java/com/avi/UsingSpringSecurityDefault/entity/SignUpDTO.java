package com.avi.UsingSpringSecurityDefault.entity;

import com.avi.UsingSpringSecurityDefault.enums.Permission;
import com.avi.UsingSpringSecurityDefault.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String name;
    private String email;
    private Set<Role> roles;
    private Set<Permission> permissions;
    private String password;

}
