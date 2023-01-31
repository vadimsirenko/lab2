package ru.vasire.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vasire.models.Book;

import java.sql.Types;
import java.util.List;

@Component
public class BookDAO {
/*
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT b.id, b.name, b.author, b.publication, b.personId, p.name as personName " +
                        "FROM book as b left join person as p on b.personId = p.id WHERE b.id =?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public List<Book> getBooksByPerson(int personId){
        return jdbcTemplate.query("SELECT id, name, author, publication, personId FROM book WHERE personId=?",
                new Object[]{personId},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getAll(){
        return jdbcTemplate.query("SELECT id, name, author, publication, personId FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void create(Book book){
        jdbcTemplate.update("INSERT INTO book (name, author, publication) VALUES (?,?,?)",
                new Object[]{book.getName(), book.getAuthor(), book.getPublication()},
                new int[]{Types.VARCHAR,Types.VARCHAR, Types.INTEGER});
    }
    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name=?, author=?, publication = ? WHERE id =?",
                new Object[]{book.getName(), book.getAuthor(), book.getPublication(), book.getId()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
    }

    public void delete(int id)
    {
        jdbcTemplate.update("DELETE FROM book WHERE id =?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

    public void setPerson(Book book) {
        jdbcTemplate.update("UPDATE book SET personId=? WHERE id =?",
                new Object[]{book.getPersonId(), book.getId()},
                new int[]{Types.INTEGER, Types.INTEGER});
    }

 */
}
