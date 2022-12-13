package org.gots.springcourse.dao;

import org.gots.springcourse.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper()); //BeanPropertyRowMapper<>(Book.class));
    }
    public Optional<Book> show(int id) {

        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",
                new Object[] {id} ,
                new int[] { Types.INTEGER },
                new BookMapper()).stream().findAny();

    }

    public void save(Book Book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?,?,?)",
                Book.getName(),
                Book.getAuthor(),
                Book.getYear());
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=?  WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }


    public void setPerson(int book_id, Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                book.getPerson_id(), book_id);
    }

}
