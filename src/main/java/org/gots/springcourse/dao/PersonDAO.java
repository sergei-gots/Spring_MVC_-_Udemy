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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new Object[] {id} ,
                new int[] { Types.INTEGER },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny();
    }

    //For vaildation by e-mail uniqueness.
    public Optional<Person> getPersonByEMail(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",
                new Object[] {email} ,
                new int[] { Types.VARCHAR },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth, email) VALUES (?,?,?)",
                person.getFull_name(),
                person.getYear_of_birth(),
                person.getEmail());
    }
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=?, email=? WHERE id=?",
                updatedPerson.getFull_name(), updatedPerson.getYear_of_birth(), updatedPerson.getEmail(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",
                new Object[] {  person_id },
                new int[] { Types.INTEGER },
                new BeanPropertyRowMapper<>(Book.class));
                //new BookMapper());
    }
}
