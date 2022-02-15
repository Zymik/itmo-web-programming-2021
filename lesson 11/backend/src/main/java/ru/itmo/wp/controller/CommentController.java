package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.NoSuchResourceException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.validator.JwtCredentialsValidator;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private final JwtCredentialsValidator commentCredentialsValidator;

    public CommentController(PostService postService,
                             CommentService commentService, JwtCredentialsValidator jwtCredentialsValidator) {
        this.postService = postService;
        this.commentService = commentService;
        this.commentCredentialsValidator = jwtCredentialsValidator;
    }

    @InitBinder("postCredentials")
    public void initRegisterBinder(WebDataBinder binder) {
        binder.addValidators(commentCredentialsValidator);
    }

    @PostMapping("posts/{id}/comments")
    public void addComment(@PathVariable long id,
                          @Valid @RequestBody CommentCredentials commentCredentials, BindingResult bindingResultPost) {
        Post post = postService.find(id);
        if (post == null) {
            throw new NoSuchResourceException("Invalid post id");
        }
        if (bindingResultPost.hasErrors()) {
            throw new ValidationException(bindingResultPost);
        }
        postService.addComment(post, commentCredentials);
    }

    @GetMapping("posts/{id}/comments")
    public List<Comment> getComments(@PathVariable long id) {
        Post post = postService.find(id);
        if (post == null) {
            throw new NoSuchResourceException("Invalid post id");
        }
        return post.getComments();
    }

    @GetMapping("posts/{id}/comments/count")
    public long count(@PathVariable long id) {
        if (!postService.existsById(id)) {
            throw new NoSuchResourceException("Invalid post id");
        }
        return commentService.countByPostId(id);
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handler() {
        throw new NoSuchResourceException("Invalid post id");
    }
}
