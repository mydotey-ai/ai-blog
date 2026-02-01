package com.mydotey.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotBlank
    private String content;

    private String summary;
    private String coverImage;
    private String status = "DRAFT";
    private Set<String> tagNames;
}
