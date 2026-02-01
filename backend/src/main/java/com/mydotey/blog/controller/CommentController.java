package com.mydotey.blog.controller;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreateCommentRequest;
import com.mydotey.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getComments(@RequestParam Long postId) {
        return commentService.getApprovedComments(postId);
    }

    @PostMapping
    public CommentDTO createComment(@Valid @RequestBody CreateCommentRequest request, HttpServletRequest httpRequest) {
        return commentService.createComment(request, httpRequest);
    }
}
