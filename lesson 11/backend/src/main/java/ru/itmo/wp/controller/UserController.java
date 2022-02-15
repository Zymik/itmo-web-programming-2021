package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.form.validator.UserRegisterCredentialsValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;
import ru.itmo.wp.exception.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserCredentialsEnterValidator userCredentialsEnterValidator;
    private final UserRegisterCredentialsValidator userRegisterCredentialsValidator;

    public UserController(JwtService jwtService, UserService userService, UserCredentialsEnterValidator userCredentialsEnterValidator, UserRegisterCredentialsValidator userRegisterCredentialsValidator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
        this.userRegisterCredentialsValidator = userRegisterCredentialsValidator;
    }

    @InitBinder("userCredentials")
    public void initEnterBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsEnterValidator);
    }

    @InitBinder("userRegisterCredentials")
    public void initRegisterBinder(WebDataBinder binder) {
        binder.addValidators(userRegisterCredentialsValidator);
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @PostMapping("users")
    public void register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        userService.register(userRegisterCredentials);
    }

    @GetMapping("users")
    public List<User> getAll() {
        return userService.findAll();
    }
}
