package ru.osipov.webPractice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.osipov.webPractice.DAO.BookDao;
import ru.osipov.webPractice.DAO.PersonDao;
import ru.osipov.webPractice.models.Book;
import ru.osipov.webPractice.models.Person;
import ru.osipov.webPractice.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private BookValidator bookValidator;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("person", bookDao.showOwner(id));
        model.addAttribute("people", personDao.index());
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String toFreeBook(@PathVariable("id") int id) {
        bookDao.toFreeBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/addBook")
    public String addBook(@PathVariable("id") int id, @ModelAttribute Person person) {
        bookDao.addBook(person.getId(), id);
        return "redirect:/books";
    }



}
