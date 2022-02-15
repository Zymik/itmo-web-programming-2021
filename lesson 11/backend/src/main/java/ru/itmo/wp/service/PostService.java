package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public PostService(PostRepository postRepository, UserRepository userRepository, JwtService jwtService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public void post(PostCredentials postCredentials) {
        Post post = new Post();
        User user = jwtService.find(postCredentials.getJwt());
        post.setUser(user);
        post.setText(postCredentials.getText());
        post.setTitle(postCredentials.getText());
        postRepository.save(post);
        userRepository.save(user);
    }

    public Post find(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public boolean existsById(long id) {
        return postRepository.existsById(id);
    }
    public void addComment(Post post, CommentCredentials commentCredentials) {
        User user = jwtService.find(commentCredentials.getJwt());
        Comment comment = new Comment();
        comment.setText(commentCredentials.getText());
        comment.setUser(user);
        post.addComment(comment);
        postRepository.save(post);
    }
}
