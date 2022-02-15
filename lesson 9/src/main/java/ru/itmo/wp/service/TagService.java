package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public Tag getTagAndAddIfNotExist(String tag) {
        Tag t = tagRepository.findByName(tag);
        if (t == null) {
            t = tagRepository.save(new Tag(tag));
        }
       return t;
    }
}
