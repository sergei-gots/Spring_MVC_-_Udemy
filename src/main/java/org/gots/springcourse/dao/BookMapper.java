package org.gots.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;

import org.gots.springcourse.models.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYear(resultSet.getInt("year"));
        book.setPerson_id(resultSet.getInt("person_id"));

        return book;
    }
}
