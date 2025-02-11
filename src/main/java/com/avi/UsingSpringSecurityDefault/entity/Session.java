package com.avi.UsingSpringSecurityDefault.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;
}
