package com.mydotey.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @NotNull
    private Long postId;

    private Long parentId;

    private String authorName;
    private String authorEmail;

    @NotBlank
    private String content;
}
