package org.gots.springcourse.dao;

import org.gots.springcourse.models.Person;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    //Normally we store the next final parameters are stored in a secured .property-file:
    //URL consist of 3 parts: <driver-name>://<host:port>/<db-name>:
    private static final String URL="jdbc:postgresql://localhost:5432/first_db";
    private static final String username="postgres";
    private static final String password="postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
                Statement statement = connection.createStatement();
                String SQL = "SELECT * FROM Person";
                ResultSet resultSet = statement.executeQuery(SQL);

                while(resultSet.next()) {
                    Person person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setName(resultSet.getString("name"));
                    person.setAge(resultSet.getInt("age"));
                    person.setEmail(resultSet.getString("email"));

                    people.add(person);
;                }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return people;

    }

    public Person show(int id) {
/*        return people.stream().
                filter(person -> person.getId() == id).
                    findAny().orElse(null);*/
        Person person = new Person();
        return person;
    }

    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES ("
                    + (++PEOPLE_COUNT) +
                    ",'" + person.getName() +
                    "'," + person.getAge() +
                    ",'" + person.getEmail() + "')";
            System.out.println("SQL = " + SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person editedPerson) {
        /*Person personToBeUpdated = show(id);
        personToBeUpdated.setName(editedPerson.getName());
        personToBeUpdated.setAge(editedPerson.getAge());
        personToBeUpdated.setEmail(editedPerson.getEmail());*/
    }

    public void delete(int id) {
        /*people.removeIf(p -> p.getId()==id);*/
    }
}
