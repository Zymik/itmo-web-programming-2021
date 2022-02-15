package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void save(Talk event) {
        talkRepository.save(event);
    }

    public List<Talk> findByUserId(long id) {
        return talkRepository.findBySourceOrTargetUserId(id);
    }

    public void validate(User targetUser, String text) throws ValidationException {
        if (targetUser == null) {
            throw new ValidationException("Incorrect target user");
        }

        if (Strings.isNullOrEmpty(text.trim())) {
            throw new ValidationException("Entered empty message");
        }

        if (text.length() > 255) {
            throw new ValidationException("Too long input");
        }
    }
}
