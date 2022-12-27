package org.gots.springcourse.controllers;


import javax.validation.Valid;
import org.gots.springcourse.dao.BookDAO;
import org.gots.springcourse.dao.PersonDAO;
import org.gots.springcourse.models.Book;
import org.gots.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final BookDAO bookDAO;
    @Autowired
    private final PersonDAO personDAO;
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model,
                       @PathVariable("id") int id) {
        Book book = bookDAO.show(id).get();
        model.addAttribute("book", book);

        Optional<Person> optOwner = bookDAO.getOwner(book);

        if(optOwner.isEmpty()) {
            model.addAttribute("people", personDAO.index());
            model.addAttribute("selectedPerson", new Person());
        } else {
            model.addAttribute("owner", optOwner.get());
        }
        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("Book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id).get());

        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if(bindingResult.hasErrors()) {
            return "/books/edit";
        }
        bookDAO.update(id, book);
        return  "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("book") Book book,
                         @ModelAttribute("person") Person selectedPerson,
                         @PathVariable("id") int id) {

        bookDAO.assign(id, selectedPerson);
        return  "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@ModelAttribute("book") Book book,
                               @PathVariable("id") int id) {

        bookDAO.release(id);
        return  "redirect:/books/" + id;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
