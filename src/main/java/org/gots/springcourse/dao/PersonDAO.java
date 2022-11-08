package org.gots.springcourse.dao;

import org.gots.springcourse.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 52,    "bob@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 18,   "mike@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 34,   "katy@gmail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().
                filter(person -> person.getId() == id).
                    findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person editedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(editedPerson.getName());
        personToBeUpdated.setAge(editedPerson.getAge());
        personToBeUpdated.setEmail(editedPerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId()==id);
    }
}
 