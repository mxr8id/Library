package ru.osipov.webPractice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.osipov.webPractice.models.Book;
import ru.osipov.webPractice.services.BookService;
import ru.osipov.webPractice.services.PersonService;
import ru.osipov.webPractice.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private PersonService personService;
    @Autowired
    private BookValidator bookValidator;

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                        Model model) {
        if (page != null && booksPerPage != null) {
            model.addAttribute("books", bookService.indexWithPaging(page, booksPerPage, sortByYear));
        } else {
            model.addAttribute("books", bookService.indexWithoutPaging(page, booksPerPage, sortByYear));
        }
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
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("person", bookService.showOwner(id));
        model.addAttribute("people", personService.index());
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String toFreeBook(@PathVariable("id") int id) {
        bookService.toFreeBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/addBook")
    public String addBook(@PathVariable("id") int id, @RequestParam("personId") int personId) {
        bookService.addBook(personId, id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("books", bookService.search(query));
        return "books/search";
    }


}
