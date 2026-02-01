package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    private Long parentId;

    @Column(length = 50)
    private String authorName;

    @Column(length = 100)
    private String authorEmail;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 20)
    private String status = "PENDING";

    @Column(length = 45)
    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String userAgent;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
