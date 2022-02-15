package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.form.AbilityChangeRequest;
import ru.itmo.wp.lesson8.form.validator.AbilityChangeRequestValidator;
import ru.itmo.wp.lesson8.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Controller
public class UsersPage extends Page {
    private final UserService userService;


    private final AbilityChangeRequestValidator abilityChangeRequestValidator;

    public UsersPage(UserService userService, AbilityChangeRequestValidator abilityChangeRequestValidator) {
        this.userService = userService;
        this.abilityChangeRequestValidator = abilityChangeRequestValidator;
    }

    @InitBinder("changeAbility")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(abilityChangeRequestValidator);
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("changeAbility", new AbilityChangeRequest());
        return "UsersPage";
    }


    @ModelAttribute("users")
    private List<User> putUsersInModel() {
        return userService.findAll();
    }

    private void putErrorsInModel(BindingResult result, Model model) {
        if (result.hasFieldErrors("type")) {
            model.addAttribute("typeError", "Invalid operation with user");
        }

        if (result.hasFieldErrors("userId")) {
            model.addAttribute("idError", "Invalid user");
        }
    }

    @PostMapping(value = "/users/all", params = {"type", "userId"})
    public String changeAbility(@Valid @ModelAttribute("changeAbility")
                                        AbilityChangeRequest abilityChangeRequest,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            putErrorsInModel(result, model);
            return "UsersPage";
        }
        boolean disableValue = abilityChangeRequest.getType().equals("disable");
        userService.setDisable(abilityChangeRequest.getUserId(), disableValue);
        return "redirect:/users/all";
    }
}
