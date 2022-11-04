package org.gots.springcourse.controllers;

import org.gots.springcourse.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        //Get all the people from DAO and pass them to the view  with Thymeleaf
        model.addAttribute("people", personDAO.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        //Get a person by their id from DAO and pass them to the view  with Thymeleaf
        model.addAttribute("person", personDAO.show(id));
        return "/people/show";
    }
}
