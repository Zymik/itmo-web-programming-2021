package ru.itmo.wp.lesson8.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class AbilityChangeRequest {
    @Pattern(regexp = "disable|enable", message = "Change request must be " +
            "disable or enable")
    private String type;

    @Min(1)
    private long userId;

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public long getUserId() {
        return userId;
    }
}
