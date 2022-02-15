package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

/** @noinspection UnstableApiUsage*/
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password, String passwordConfirm) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (user.getEmail().chars().filter(x -> x == '@').count() != 1) {
            throw new ValidationException("Invalid email form");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }

        if (!password.equals(passwordConfirm)) {
            throw new ValidationException("Passwords aren't same");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, getPasswordSha(password));
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
        }
        if (user == null) {
            throw new ValidationException("Invalid login/email or password");
        }
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(login, getPasswordSha(password));
        }
        return user;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public long findCount() {
        return userRepository.countAll();
    }

    public User find(long id) {
        return userRepository.find(id);
    }
}
