package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.TagStringList;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.TagService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final TagService tagService;

    public WritePostPage(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("tags", new TagStringList());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("tags") TagStringList tags,
                                BindingResult bindingResultTags,
                                @Valid @ModelAttribute("post") Post post,
                                BindingResult bindingResultPost,
                                 HttpSession httpSession,
                                Model model) {
        if (bindingResultPost.hasErrors() || bindingResultTags.hasErrors()) {
            if (bindingResultTags.hasErrors()) {
                model.addAttribute("tagError", "tags can " +
                        "contain only latin letters");
            }
            return "WritePostPage";
        }
        Set<Tag> tagSet = tags.getTags().stream()
                .map(String::toLowerCase)
                .distinct()
                .map(tagService::getTagAndAddIfNotExist)
                .collect(Collectors.toSet());
        post.setTagSet(tagSet);
        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");
        return "redirect:/posts";
    }
}
