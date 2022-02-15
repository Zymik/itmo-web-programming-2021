package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    public record TalkView(long id, String targetUser, String sourceUser, String text, Date creationTime) {
    }

    private List<TalkView> toTalkRecordList(List<Talk> talks) {
        return talks.stream()
                .map(x -> new TalkView(x.getId(),
                        userService.find(x.getTargetUserId()).getLogin(),
                        userService.find(x.getSourceUserId()).getLogin(),
                        x.getText(), x.getCreationTime()))
                .toList();
    }

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() == null) {
            setMessage("Talks available only for logged in users");
            throw new RedirectException("/index");
        }
        List<Talk> talks = talkService.findByUserId(getUser().getId());
        view.put("talks", toTalkRecordList(talks));
        view.put("users", userService.findAll());
        super.before(request, view);
    }


    protected void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User targetUser = userService.findByLogin(request.getParameter("targetUser"));
        String text = request.getParameter("text").trim();
        talkService.validate(targetUser, text);
        Talk talk = new Talk();
        talk.setText(text);
        talk.setTargetUserId(targetUser.getId());
        talk.setSourceUserId(getUser().getId());
        talkService.save(talk);
        setMessage("Message was \n successfully delivered to " + request.getParameter("targetUser"));
        throw new RedirectException("/talks");
    }
}
