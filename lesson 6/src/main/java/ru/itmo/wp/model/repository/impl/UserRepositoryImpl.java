package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    private final static String CAN_NOT_FIND_ERROR = "Can't find User.";
    private final static String CAN_NOT_SAVE_ERROR = "Can't save User.";

    private  final MissingParametersSetter<User> setter = (generatedKeys, instance) -> {
        instance.setId(generatedKeys.getLong(1));
        instance.setCreationTime(find(instance.getId()).getCreationTime());
    };

    @Override
    public User find(long id) {
        PatternFiller patternFiller = statement -> statement.setLong(1, id);
        return findBy("SELECT * FROM User WHERE id=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public User findByLogin(String login) {
        PatternFiller patternFiller = statement -> statement.setString(1, login);
        return findBy("SELECT * FROM User WHERE login=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        PatternFiller patternFiller = statement -> {
            statement.setString(1, login);
            statement.setString(2, passwordSha);
        };
        return findBy("SELECT * FROM User WHERE login=? AND passwordSha=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        PatternFiller patternFiller = statement -> {
            statement.setString(1, email);
            statement.setString(2, passwordSha);
        };
        return findBy("SELECT * FROM User WHERE email=? AND passwordSha=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public List<User> findAll() {
        return findListBy("SELECT * FROM User ORDER BY id DESC", x -> {}, CAN_NOT_FIND_ERROR);
    }

    @Override
    public User findByEmail(String email) {
        PatternFiller patternFiller = statement -> statement.setString(1, email);
        return findBy("SELECT * FROM User WHERE email=?",
                patternFiller, CAN_NOT_FIND_ERROR);
    }

    @Override
    public void save(User user, String passwordSha) {
        PatternFiller patternFiller = statement -> {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, passwordSha);
        };
        save(user, "INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())",
                patternFiller, setter, CAN_NOT_SAVE_ERROR);
    }

    @Override
    public long countAll() {
        PatternFiller filler = x -> {};
        return countBy("SELECT COUNT(*) FROM `User`", filler, CAN_NOT_FIND_ERROR);
    }

    @Override
    protected User toInstance(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> user.setId(resultSet.getLong(i));
                case "login" -> user.setLogin(resultSet.getString(i));
                case "email" -> user.setEmail(resultSet.getString(i));
                case "creationTime" -> user.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                }
                // No operations.
            }
        }

        return user;
    }
}
