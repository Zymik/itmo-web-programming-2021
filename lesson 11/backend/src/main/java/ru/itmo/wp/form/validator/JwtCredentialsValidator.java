package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.JwtCredentials;
import ru.itmo.wp.service.JwtService;
@Component
public class JwtCredentialsValidator implements Validator {
    private final JwtService jwtService;

    public JwtCredentialsValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean supports(Class<?> clazz) {
        return JwtCredentials.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            JwtCredentials jwtCredentials = (JwtCredentials) target;
            if (jwtService.find(jwtCredentials.getJwt()) == null) {
                errors.reject("invalid-user", "User didn't log in");
            }
        }
    }
}
