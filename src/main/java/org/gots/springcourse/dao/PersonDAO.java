package org.gots.springcourse.dao;

import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

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

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new Object[] {id} ,
                new int[] { Types.INTEGER },
                new BeanPropertyRowMapper<>(Person.class) ).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)", 1, person.getName(), person.getAge(), person.getEmail());
    }

    public void save(List<Person> people, int count) {
        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Person person = people.get(i);
                ps.setInt(1, i);
                ps.setString(2, person.getName());
                ps.setInt(3, person.getAge());
                ps.setString(4, person.getEmail());

            }

            @Override
            public int getBatchSize() {
                return count;
            }
        };

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?,?,?,?)",
            batchPreparedStatementSetter);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
