package com.mydotey.blog.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private Long postId;
    private Long parentId;
    private String authorName;
    private String content;
    private String status;
    private LocalDateTime createdAt;
}
