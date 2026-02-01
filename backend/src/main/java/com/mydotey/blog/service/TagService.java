package com.mydotey.blog.service;

import com.mydotey.blog.entity.Tag;
import com.mydotey.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setSlug(name.toLowerCase().replaceAll("\\s+", "-"));
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
