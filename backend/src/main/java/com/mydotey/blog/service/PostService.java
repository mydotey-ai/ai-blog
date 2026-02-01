package com.mydotey.blog.service;

import com.mydotey.blog.dto.CreatePostRequest;
import com.mydotey.blog.dto.PostDTO;
import com.mydotey.blog.entity.Post;
import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.exception.ResourceNotFoundException;
import com.mydotey.blog.repository.PostRepository;
import com.mydotey.blog.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public Page<PostDTO> getPublishedPosts(Pageable pageable) {
        return postRepository.findByStatus("PUBLISHED", pageable).map(this::toDTO);
    }

    public Page<PostDTO> getPostsByTag(String tagSlug, Pageable pageable) {
        return postRepository.findByTagSlugAndStatus(tagSlug, "PUBLISHED", pageable).map(this::toDTO);
    }

    public Page<PostDTO> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchByKeyword(keyword, "PUBLISHED", pageable).map(this::toDTO);
    }

    @Transactional
    public PostDTO getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
            .filter(p -> "PUBLISHED".equals(p.getStatus()))
            .orElseThrow(() -> new ResourceNotFoundException("文章未找到或未发布: " + slug));
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
        return toDTO(post);
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("文章未找到: ID " + id));
        return toDTO(post);
    }

    @Transactional
    public PostDTO createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setCoverImage(request.getCoverImage());
        post.setStatus(request.getStatus());

        if (request.getTagNames() != null) {
            Set<Tag> tags = request.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag tag = new Tag();
                        tag.setName(name);
                        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
                        return tagRepository.save(tag);
                    }))
                .collect(Collectors.toSet());
            post.setTags(tags);
        }

        return toDTO(postRepository.save(post));
    }

    @Transactional
    public PostDTO updatePost(Long id, CreatePostRequest request) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("文章未找到: ID " + id));

        post.setTitle(request.getTitle());
        post.setSlug(request.getSlug());
        post.setContent(request.getContent());
        post.setSummary(request.getSummary());
        post.setCoverImage(request.getCoverImage());
        post.setStatus(request.getStatus());

        if (request.getTagNames() != null) {
            Set<Tag> tags = request.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag tag = new Tag();
                        tag.setName(name);
                        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
                        return tagRepository.save(tag);
                    }))
                .collect(Collectors.toSet());
            post.setTags(tags);
        }

        return toDTO(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Page<PostDTO> getAllPostsForAdmin(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::toDTO);
    }

    private PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setContent(post.getContent());
        dto.setSummary(post.getSummary());
        dto.setCoverImage(post.getCoverImage());
        dto.setStatus(post.getStatus());
        dto.setViews(post.getViews());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        if (post.getTags() != null) {
            dto.setTags(post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        }

        return dto;
    }
}
