package org.gots.springcourse.dao;

import org.gots.springcourse.models.Book;
import org.gots.springcourse.models.Person;
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

        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                new Object[] {id} ,
                new int[] { Types.INTEGER },
                new BookMapper()).stream().findAny();

    }
    public void save(Book Book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?,?,?)",
                Book.getTitle(),
                Book.getAuthor(),
                Book.getYear());
    }
    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=?  WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void assign(int book_id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectedPerson.getId(), book_id);
    }
    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }
    public Optional<Person> getOwner(Book book) {
        String [] queries  = new String [] {
             "SELECT * FROM Person Where id=?",
                    "SELECT Person.* FROM Book JOIN Person On Book.person_id=Person.Id Where Book.id=?"
        };
        int [] args = new int[] {
                book.getPerson_id(),
                book.getId()
        };
        int variant = 1;

        return jdbcTemplate.query(queries[variant],
                new Object[] { args[variant] } ,
                new int[] { Types.INTEGER },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny();
    }
}
