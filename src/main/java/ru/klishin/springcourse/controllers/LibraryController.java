package ru.klishin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.klishin.springcourse.dao.BookDAO;
import ru.klishin.springcourse.models.Book;
import ru.klishin.springcourse.models.Person;
import ru.klishin.springcourse.services.LibraryService;
import ru.klishin.springcourse.services.PeopleService;
import ru.klishin.springcourse.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class LibraryController {

//    private final PersonDAO personDAO;
//    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final LibraryService libraryService;
    private final PeopleService peopleService;

    @Autowired
    public LibraryController(BookValidator bookValidator, LibraryService libraryService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.libraryService = libraryService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("library", libraryService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {

        if(libraryService.findById(id).getPerson() != null)
            model.addAttribute("reader", peopleService.findById(libraryService.findById(id).getPerson().getPersonId()));

        model.addAttribute("book", libraryService.findById(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/new";

        libraryService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", libraryService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "books/edit";

        libraryService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/appoint")
    public String appointBook(@ModelAttribute("person") Person person, @PathVariable("id") int book_id) {
        libraryService.appointBook(person, libraryService.findById(book_id));
        return "redirect:/books";
    }

    @PatchMapping("/{id}/freeing")
    public String freeingBook(@PathVariable("id") int book_id) {
        libraryService.freeingBook(libraryService.findById(book_id));
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        libraryService.delete(id);
        return "redirect:/books";
    }
}
