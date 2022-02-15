package ru.itmo.wp.model.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

interface PatternFiller {
    void fill(PreparedStatement statement) throws SQLException;
}
