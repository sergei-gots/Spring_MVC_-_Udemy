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
                       @PathVariable("id") int id,
                       @ModelAttribute Person person) {
        //Get a Book by their id from DAO and pass them to the view  with Thymeleaf
        Optional<Book> opt = bookDAO.show(id);
        if(opt.isEmpty()) {
            System.out.println("opt is empty");
            return "redirect:/books";
        }

        Book book = opt.get();
        model.addAttribute("book", book);
        if(book.isAvailable()) {
            model.addAttribute("people", personDAO.index());
        } else {
            model.addAttribute("reader", personDAO.show(book.getPerson_id()));
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
        Optional<Book> opt = bookDAO.show(id);
        if(opt.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", opt.get());

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

    @PatchMapping("/{id}/assign-reader")
    public String assignReader(@ModelAttribute("book") Book book,
                               @ModelAttribute("person") Person person,
                         @PathVariable("id") int id) {

        bookDAO.assignReader(id, person.getId());
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
