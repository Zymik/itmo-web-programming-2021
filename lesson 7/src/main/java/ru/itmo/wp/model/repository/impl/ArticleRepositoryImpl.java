package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepositoryImpl extends AbstractRepository<Article> implements ArticleRepository {
    private final static String CAN_NOT_FIND_ERROR = "Can't find Article.";
    private final static String CAN_NOT_SAVE_ERROR = "Can't save Article.";
    private final static String CAN_NOT_UPDATE_ERROR = "Can't update Article.";

    MissingParametersSetter<Article> setter = (generatedKeys, instance) -> {
        instance.setId(generatedKeys.getLong(1));
        instance.setCreationTime(find(instance.getId()).getCreationTime());
    };

    @Override
    protected Article toInstance(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id" -> article.setId(resultSet.getLong(i));
                case "userId" -> article.setUserId(resultSet.getLong(i));
                case "title" -> article.setTitle(resultSet.getString(i));
                case "text" -> article.setText(resultSet.getString(i));
                case "hidden" -> article.setHidden(resultSet.getBoolean(i));
                case "creationTime" -> article.setCreationTime(resultSet.getTimestamp(i));
                default -> {
                }
                // No operations.
            }
        }

        return article;
    }

    @Override
    public void save(Article article) {
        PatternFiller patternFiller = statement -> {
            statement.setLong(1, article.getUserId());
            statement.setString(2, article.getTitle());
            statement.setString(3, article.getText());
            statement.setBoolean(4, article.isHidden());
        };
        save(article, "INSERT INTO `Article` (`userId`, `title`, `text`, `creationTime`, `hidden`) VALUES (?, ?, ?, NOW(), ?)",
                patternFiller, setter, CAN_NOT_SAVE_ERROR);

    }

    @Override
    public Article find(long id) {
        PatternFiller filler = statement -> statement.setLong(1, id);
        return findBy("SELECT * FROM Article WHERE id=?", filler, CAN_NOT_FIND_ERROR);
    }

    @Override
    public List<Article> findAll() {
        return findListBy("SELECT * FROM Article ORDER BY id DESC", x -> {
        }, CAN_NOT_FIND_ERROR);
    }

    @Override
    public List<Article> findAllByTime() {
        return findListBy("SELECT * FROM Article WHERE hidden=false ORDER BY creationTime DESC", x -> {
        }, CAN_NOT_FIND_ERROR);
    }

    @Override
    public List<Article> findByUserId(long userId) {
        return findListBy("SELECT * FROM Article WHERE userId=? ORDER BY id DESC",
                x -> x.setLong(1, userId), CAN_NOT_FIND_ERROR);
    }

    @Override
    public void changeHidden(Article article, boolean value) {
        PatternFiller filler = statement ->
        {
            statement.setBoolean(1, value);
            statement.setLong(2, article.getId());
        };
        update("UPDATE Article SET hidden=? WHERE id=?", filler, CAN_NOT_UPDATE_ERROR);
    }

    @Override
    public boolean contains(long id) {
        PatternFiller filler = statement ->
        {
            statement.setLong(1, id);
        };
        return countBy("SELECT COUNT(*) FROM Article WHERE id=?", filler, CAN_NOT_FIND_ERROR) >= 1;
    }
}
