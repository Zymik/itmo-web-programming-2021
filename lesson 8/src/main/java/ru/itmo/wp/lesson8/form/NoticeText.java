package ru.itmo.wp.lesson8.form;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoticeText {

    @NotNull
    @NotBlank
    @Size(min = 4, max = 255)
    private String noticeText;

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText.trim();
    }

    public String getNoticeText() {
        return noticeText;
    }
}
