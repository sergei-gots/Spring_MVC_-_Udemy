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

    @GetMapping("")
    public String showListToSelectAdmin(Model model) {
        model.addAttribute(("person"), new Person());
        model.addAttribute("people", personDAO.index());
        return "/admin/select-adm";
    }

    @PatchMapping("/select-adm")
    public String makeAdmin(@ModelAttribute Person person) {
        personDAO.makeAdmin(person.getId());
        return "redirect:/people";
    }

    @GetMapping("/show")
    public String index(Model model) {
        model.addAttribute("people", personDAO.indexAdmin());
        return "/index";
    }
}
