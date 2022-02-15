package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.NoticeText;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticesPage extends Page {
    private final NoticeService noticeService;

    public NoticesPage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @GetMapping("/notices")
    public String noticesGet(Model model) {
        model.addAttribute("noticeText", new NoticeText());
        return "NoticesPage";
    }


    @PostMapping("/notices")
    public String noticesPost(@Valid @ModelAttribute("noticeText") NoticeText noticeText,
                              BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticesPage";
        }
        noticeService.save(noticeText);
        setMessage(httpSession, "Notice was created");
        return "redirect:";
    }
}
