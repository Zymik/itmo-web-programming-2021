package ru.itmo.wp.model.repository.impl;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends AbstractRepository<Talk> implements TalkRepository {
    private final static String CAN_NOT_FIND_ERROR = "Can't find Talk.";
    private final static String CAN_NOT_SAVE_ERROR = "Can't save Talk.";
    private  final MissingParametersSetter<Talk> setter = (generatedKeys, instance) -> {
        instance.setId(generatedKeys.getLong(1));
        instance.setCreationTime(find(instance.getId()).getCreationTime());
    };

    @Override
    public Talk find(long id) {
        PatternFiller patternFiller = statement -> statement.setLong(1, id);
        return findBy("SELECT * FROM Talk WHERE id=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public List<Talk> findBySourceOrTargetUserId(long userId) {
        PatternFiller patternFiller = statement -> statement.setLong(1, userId);
        return findListBy("SELECT * FROM Talk " +
                        "WHERE ? IN (targetUserId, sourceUserId) ORDER BY creationTime DESC",
                patternFiller, CAN_NOT_FIND_ERROR);
    }


    @Override
    public void save(Talk talk) {
        PatternFiller patternFiller = statement -> {
            statement.setLong(1, talk.getSourceUserId());
            statement.setLong(2, talk.getTargetUserId());
            statement.setString(3, talk.getText());
        };
        save(talk, "INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())",
                patternFiller,setter, CAN_NOT_SAVE_ERROR);
    }

    @Override
    protected Talk toInstance(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> talk.setId(resultSet.getLong(i));
                case "targetUserId" -> talk.setTargetUserId(resultSet.getLong(i));
                case "sourceUserId" -> talk.setSourceUserId(resultSet.getLong(i));
                case "text" -> talk.setText(resultSet.getString(i));
                case "creationTime" -> talk.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                }
                // No operations.
            }
        }

        return talk;
    }
}
