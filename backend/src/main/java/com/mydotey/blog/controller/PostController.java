package com.mydotey.blog.controller;

import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostDTO> getPosts(
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String search,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        if (tag != null && !tag.isEmpty()) {
            return postService.getPostsByTag(tag, pageRequest);
        } else if (search != null && !search.isEmpty()) {
            return postService.searchPosts(search, pageRequest);
        } else {
            return postService.getPublishedPosts(pageRequest);
        }
    }

    @GetMapping("/{slug}")
    public PostDTO getPost(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }
}
