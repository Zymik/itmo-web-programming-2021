package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.NoticeText;

@Component
public class NoticePostValidator implements Validator {


    public boolean supports(Class<?> clazz) {
        return NoticeText.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            String notice = ((NoticeText) target).getNoticeText();
            if (notice.isBlank()) {
                errors.rejectValue("noticeText", "noticeText.emptyOrOnlyWhitespaces",
                        "Empty notice text");
            } else if (notice.trim().length() < 5) {
                errors.rejectValue("noticeText", "noticeText.lessThanFiveSymbols",
                        "Notice must contains at least 5 symbols");
            }

        }
    }
}
