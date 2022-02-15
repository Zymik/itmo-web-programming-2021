package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.repository.EventRepository;
import java.sql.*;


public class EventRepositoryImpl extends AbstractRepository<Event> implements EventRepository {
    private static final String CAN_NOT_FIND_ERROR = "Can't find Event.";
    private static final String CAN_NOT_SAVE_ERROR = "Can't save Event.";

    private  final MissingParametersSetter<Event> setter = (generatedKeys, instance) -> {
        instance.setId(generatedKeys.getLong(1));
        instance.setCreationTime(find(instance.getId()).getCreationTime());
    };

    @Override
    public void save(Event event) {
        PatternFiller patternFiller = statement -> {
            statement.setString(1, Long.toString(event.getUserId()));
            statement.setString(2, event.getEventType().name());
        };
        save(event, "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES ( ?, ?, NOW())",
                patternFiller, setter, CAN_NOT_SAVE_ERROR);
    }

    public Event find(long id) {
        PatternFiller patternFiller = statement -> statement.setLong(1, id);
        return findBy("SELECT * FROM Event WHERE id=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }


    @Override
    protected Event toInstance(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> event.setId(resultSet.getLong(i));
                case "userId" -> event.setUserId(resultSet.getLong(i));
                case "type" -> event.setEventType(EventType.valueOf(resultSet.getString(i)));
                case "creationTime" -> event.setCreationTime(resultSet.getTimestamp(i));
            }
        }

        return event;
    }
}
