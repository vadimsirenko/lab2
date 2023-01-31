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

    public List<Book> findAll() {
        return bookRepository.findAll(PageRequest.of(1, 3, Sort.by("publication"))).getContent();
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
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void chekout(int bookId, int personId){
        Person person = peopleRepository.findById(personId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
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