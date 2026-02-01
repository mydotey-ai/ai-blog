package com.mydotey.blog.service;

import com.mydotey.blog.dto.CommentDTO;
import com.mydotey.blog.dto.CreateCommentRequest;
import com.mydotey.blog.entity.Comment;
import com.mydotey.blog.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> getApprovedComments(Long postId) {
        return commentRepository.findByPostIdAndStatus(postId, "APPROVED")
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public CommentDTO createComment(CreateCommentRequest request, HttpServletRequest httpRequest) {
        Comment comment = new Comment();
        comment.setPostId(request.getPostId());
        comment.setParentId(request.getParentId());
        comment.setAuthorName(request.getAuthorName());
        comment.setAuthorEmail(request.getAuthorEmail());
        comment.setContent(request.getContent());
        comment.setStatus("PENDING");
        comment.setIpAddress(getClientIp(httpRequest));
        comment.setUserAgent(httpRequest.getHeader("User-Agent"));

        return toDTO(commentRepository.save(comment));
    }

    public Page<CommentDTO> getAllCommentsForAdmin(Pageable pageable) {
        return commentRepository.findAll(pageable).map(this::toDTO);
    }

    public CommentDTO updateCommentStatus(Long id, String status) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setStatus(status);
        return toDTO(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPostId());
        dto.setParentId(comment.getParentId());
        dto.setAuthorName(comment.getAuthorName());
        dto.setContent(comment.getContent());
        dto.setStatus(comment.getStatus());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}
