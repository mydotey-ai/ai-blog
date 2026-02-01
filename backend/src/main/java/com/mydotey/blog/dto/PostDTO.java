package com.mydotey.blog.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private String coverImage;
    private String status;
    private Long views;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
