package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndStatus(Long postId, String status);
    Page<Comment> findByStatus(String status, Pageable pageable);
}
