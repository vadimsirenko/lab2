package ru.vasire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vasire.models.Person;

import jakarta.validation.Valid;
import ru.vasire.services.BookService;
import ru.vasire.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final BookService bookService;

    public PersonController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String showNew(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping("")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        Person person = peopleService.findOne(id);

        model.addAttribute("person", person);
        model.addAttribute("books", bookService.getBooksByPersonId(id));
        return "people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult) {
        //personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }
}
