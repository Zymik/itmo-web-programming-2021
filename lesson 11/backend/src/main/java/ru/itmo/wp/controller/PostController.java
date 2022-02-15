package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.validator.JwtCredentialsValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final JwtCredentialsValidator jwtCredentialsValidator;

    public PostController(PostService postService, JwtService jwtService, JwtCredentialsValidator jwtCredentialsValidator) {
        this.postService = postService;
        this.jwtCredentialsValidator = jwtCredentialsValidator;
    }

    @InitBinder("postCredentials")
    public void initRegisterBinder(WebDataBinder binder) {
        binder.addValidators(jwtCredentialsValidator);
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public void writePost(@Valid @RequestBody PostCredentials postCredentials,
                          BindingResult bindingResultPost) {
        if (bindingResultPost.hasErrors()) {
            throw new ValidationException(bindingResultPost);
        }
        postService.post(postCredentials);
    }
}
