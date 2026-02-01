package com.mydotey.blog.repository;

import com.mydotey.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);

    Page<Post> findByStatus(String status, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.slug = :tagSlug AND p.status = :status")
    Page<Post> findByTagSlugAndStatus(String tagSlug, String status, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.status = :status AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Post> searchByKeyword(String keyword, String status, Pageable pageable);
}
