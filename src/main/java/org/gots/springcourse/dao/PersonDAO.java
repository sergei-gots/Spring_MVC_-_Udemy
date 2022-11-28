package org.gots.springcourse.dao;

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
    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",
                new Object[] {email} ,
                new int[] { Types.VARCHAR },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny();
    }
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES (?,?,?,?)",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());
    }
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void makeAdmin(int id) {
        jdbcTemplate.update( "UPDATE person SET adm_flag=TRUE WHERE id=?", id);
    }

    public List<Person> indexAdmin() {
        return jdbcTemplate.query("SELECT * FROM person WHERE adm_flag=true", new BeanPropertyRowMapper<>(Person.class));
    }
}
