package org.gots.springcourse.controllers;

import org.gots.springcourse.dao.PersonDAO;
import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final PersonDAO personDAO;

    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/select")
    public String selectAdmin(Model model) {
        model.addAttribute(("person"), new Person());
        model.addAttribute("people", personDAO.index());
        return "/admin/select";
    }

    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person") Person person) {
        personDAO.makeAdmin(person.getId());
        return "redirect:/admin";
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", personDAO.indexAdmin());
        return "/admin/index";
    }
}
