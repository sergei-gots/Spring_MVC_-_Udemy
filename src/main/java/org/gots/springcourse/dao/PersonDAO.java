package org.gots.springcourse.dao;

import org.gots.springcourse.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;

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
                    setData(person, resultSet);
                    people.add(person);
;                }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return people;

    }

    private void setData(Person person, ResultSet resultSet) throws SQLException {
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                person = new Person();
                setData(person, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(?,?,?,?)");
            preparedStatement.setInt(1, ++PEOPLE_COUNT);
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4,person.getEmail());
            preparedStatement.executeUpdate();
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
