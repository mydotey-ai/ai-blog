package com.mydotey.blog.controller;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreatePostRequest;
import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.service.CommentService;
import com.mydotey.blog.service.PostService;
import com.mydotey.blog.service.TagService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;

    public AdminController(PostService postService, CommentService commentService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    // Post Management
    @GetMapping("/posts")
    public Page<PostDTO> getPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getAllPostsForAdmin(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @PostMapping("/posts")
    public PostDTO createPost(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @PutMapping("/posts/{id}")
    public PostDTO updatePost(@PathVariable Long id, @Valid @RequestBody CreatePostRequest request) {
        return postService.updatePost(id, request);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    // Comment Management
    @GetMapping("/comments")
    public Page<CommentDTO> getComments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return commentService.getAllCommentsForAdmin(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @PutMapping("/comments/{id}/status")
    public CommentDTO updateCommentStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return commentService.updateCommentStatus(id, body.get("status"));
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    // Tag Management
    @PostMapping("/tags")
    public Tag createTag(@RequestBody Map<String, String> body) {
        return tagService.createTag(body.get("name"));
    }

    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
