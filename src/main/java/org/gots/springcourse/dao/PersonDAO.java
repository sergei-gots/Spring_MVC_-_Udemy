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

import java.util.ArrayList;
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
        jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)", person.getId(), person.getName(), person.getAge(), person.getEmail());
    }



    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
    private class TestPeople {
        final private List<Person> testPeople;
        public static final int TEST_PEOPLE_COUNT = 1000;
        //This value for age will be a SPECULATIVE SIGN of belonging to test data.
        private static final int TEST_PEOPLE_AGE = 19;

        TestPeople() {
            testPeople = new ArrayList<>(TEST_PEOPLE_COUNT);
            for(int i = 0; i < TEST_PEOPLE_COUNT; i++) {
                testPeople.add(new Person(i, "Tom-" + i, TEST_PEOPLE_AGE, "tom" + i + "@gmail.com"));
            }
        }

        List<Person> get() { return testPeople; }

        Person get(int i) {
            return testPeople.get(i);
        }
    }
    private TestPeople testPeople = new TestPeople();

    public void test1000UpdateRequests() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < TestPeople.TEST_PEOPLE_COUNT; i++) {
            save(testPeople.get(i));
        }

        long finish = System.currentTimeMillis();

        System.out.println("1000 Update-request took  = " + (finish - start) + " ms");
    }

    public void testBatchUpdate() {

        List<Person> people = testPeople.get();
        long start = System.currentTimeMillis();
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
                return TestPeople.TEST_PEOPLE_COUNT;
            }
        };

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?,?,?,?)",
                batchPreparedStatementSetter);

        long finish = System.currentTimeMillis();

        System.out.println("Batch UPDATE-request took = " + (finish - start) + " ms");
    }

    public void deleteTestBatchData() {
            //Remove rows which contains age with our SPECULATIVE SIGN of belonging to test data.
            jdbcTemplate.update( "DELETE FROM person WHERE AGE=" + TestPeople.TEST_PEOPLE_AGE);
    }

}
