package org.gots.springcourse.controllers;


import javax.validation.Valid;
import org.gots.springcourse.dao.BookDAO;
import org.gots.springcourse.models.Book;
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
    private final BookDAO BookDAO;
    public BookController(BookDAO BookDAO) {
        this.BookDAO = BookDAO;
    }

    @GetMapping()
    public String index(Model model) {
        System.out.println("@GetMapping(/books)");
        model.addAttribute("books", BookDAO.index());
        System.out.println("Now we will return string");
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model,
                       @PathVariable("id") int id) {
        //Get a Book by their id from DAO and pass them to the view  with Thymeleaf
        Optional<Book> opt = BookDAO.show(id);
        if(opt.isEmpty()) {
            return "redirect:/books";
        }

        model.addAttribute("book", opt.get());
        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("Book") @Valid Book book,
                         BindingResult bindingResult) {
        //System.out.println("book.getName() " + book.getName());
        //System.out.println("book.getAuthor() " + book.getAuthor());
        //System.out.println("book.getYear() " + book.getYear());
        if(bindingResult.hasErrors()) {
            return "/books/new";
        }
        BookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Optional<Book> opt = BookDAO.show(id);
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
        BookDAO.update(id, book);
        return  "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        BookDAO.delete(id);
        return "redirect:/books";
    }
}
