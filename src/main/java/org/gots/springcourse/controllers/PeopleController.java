package org.gots.springcourse.controllers;

import jakarta.validation.Valid;
import org.gots.springcourse.dao.PersonDAO;
import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String show(Model model,
                       @PathVariable("id") int id) {
        //Get a person by their id from DAO and pass them to the view  with Thymeleaf
        model.addAttribute("person", personDAO.show(id));
        System.out.println("show: name=" +  personDAO.show(id).getName());
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "/people/new";
        }
        System.out.println("create: name=" + person.getName());
        personDAO.save(person);
        return "redirect:/people"; //this is a redirect-way
    }

    /** Request to edit **/
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    /** Request to update that has been edited  **/
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) {
            return "/people/edit";
        }
        System.out.println("update: id=" + id);
        personDAO.update(id, person);
        return  "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
