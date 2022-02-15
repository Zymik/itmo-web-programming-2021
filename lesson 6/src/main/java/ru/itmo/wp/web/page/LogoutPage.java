package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    private final EventService eventService = new EventService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() != null) {
            Event logout = new Event();
            logout.setEventType(EventType.LOGOUT);
            logout.setUserId(getUser().getId());
            eventService.save(logout);
            removeUser();
            setMessage("Good bye. Hope to see you soon!");
        }
        throw new RedirectException("/index");
    }
}
