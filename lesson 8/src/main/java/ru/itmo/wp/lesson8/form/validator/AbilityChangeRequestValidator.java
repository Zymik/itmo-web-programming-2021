package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.AbilityChangeRequest;
import ru.itmo.wp.lesson8.service.UserService;

@Component
public class AbilityChangeRequestValidator implements Validator {
    private final UserService userService;

    public AbilityChangeRequestValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return AbilityChangeRequest.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            AbilityChangeRequest abilityChangeRequest = (AbilityChangeRequest) target;
            if (userService.findById(abilityChangeRequest.getUserId()) == null) {
                errors.rejectValue("userId", "userId.invalid-userId", "invalid user");
            }
        }
    }
}
