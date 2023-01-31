package ru.vasire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vasire.models.Book;

import jakarta.validation.Valid;
import ru.vasire.models.Person;
import ru.vasire.services.BookService;
import ru.vasire.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;


    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(required = true, defaultValue = "0") int page, @RequestParam(required = true, defaultValue = "3") int books_per_page,
                        @RequestParam(required = true, defaultValue = "false") boolean sort_by_year){
        model.addAttribute("books", bookService.findAll(page,books_per_page, sort_by_year));
        return "books/index";
    }

    @GetMapping("/new")
    public String showNew(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("book", (Book)null);
        return "books/search";
    }
    @PostMapping("/search")
    public String search(Model model, @RequestParam(required = true, defaultValue = "") String find) {
        if(find.length()!=0){
            model.addAttribute("book", bookService.search(find));
        }else{
            model.addAttribute("book", (Book)null);
        }
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult) {
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/checkout")
    public String setPerson(@ModelAttribute("book") Book book) {
        System.out.println("book = " + book);
        bookService.chekout(book);
        return "redirect:/books";
    }

    @PatchMapping("/checkin")
    public String checkin(@ModelAttribute("book") Book book) {
        bookService.chekoin(book.getId());
        return "redirect:/books";
    }
}
