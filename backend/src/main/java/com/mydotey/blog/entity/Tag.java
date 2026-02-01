package com.mydotey.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String slug;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
