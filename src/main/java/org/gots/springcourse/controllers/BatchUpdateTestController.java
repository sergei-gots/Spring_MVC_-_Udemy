package org.gots.springcourse.controllers;

import org.gots.springcourse.dao.PersonDAO;
import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/batch")
public class BatchUpdateTestController {

    private final PersonDAO personDAO;
    private List<Person> people;
    private static final int PEOPLE_COUNT = 1000;

    @Autowired
    private BatchUpdateTestController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    private void createTestPeople(){
        people = new ArrayList<>(PEOPLE_COUNT);
        for(int i = 0; i < PEOPLE_COUNT; i++) {
            people.add(new Person(i, "Tom-" + i, 19, "tom" + i + "@gmail.com"));
        }
    };

    @GetMapping("/1000requests")
    public String perform1000Requests(){

        createTestPeople();

        long start = System.currentTimeMillis();

        for(int i = 0; i < PEOPLE_COUNT; i++) {
            personDAO.save(people.get(i));
        }

        long finish = System.currentTimeMillis();

        System.out.println("1000 Update-request took  = " + (finish - start) + " ms");

        return("redirect:/people");
    }

    @GetMapping("/1BatchRequest")
    public String performBatchUpdate(){

        createTestPeople();

        long start = System.currentTimeMillis();

        personDAO.save(people, PEOPLE_COUNT);

        long finish = System.currentTimeMillis();

        System.out.println("Batch UPDATE-request took = " + (finish-start) + " ms");

        return "redirect:/people";
    }
}
