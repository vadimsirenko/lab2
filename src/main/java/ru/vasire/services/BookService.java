package ru.vasire.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vasire.models.Book;
import ru.vasire.models.Person;
import ru.vasire.repositories.BookRepository;
import ru.vasire.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(int page, int books_per_page, boolean sort_by_year) {
        if(sort_by_year)
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("publication"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public Book search(String startWord) {
        return bookRepository.findByNameStartingWith(startWord).stream().findAny().orElse(null);
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isPresent())
        {
            Book book = bookOptional.get();
            book.setName(updatedBook.getName());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublication(updatedBook.getPublication());
            bookRepository.save(book);
        }
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void chekout(Book bookToCheckout){
        Book book = bookRepository.findById(bookToCheckout.getId()).orElse(null);
        Person person = peopleRepository.findById(bookToCheckout.getPersonId()).orElse(null);

        if(person != null & book != null){
            book.setOwner(person);
            book.setCheckOutDate(new Date());
            bookRepository.save(book);
        }
    }

    @Transactional
    public void chekoin(int bookId){
        Book bookObj = bookRepository.findById(bookId).orElse(null);
        if(bookObj != null)
        {
            bookObj.setOwner(null);
            bookObj.setCheckOutDate(null);
            bookRepository.save(bookObj);
        }
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }else{
            return Collections.emptyList();
        }
    }
}