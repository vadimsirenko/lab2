package ru.vasire.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vasire.models.Person;

import java.sql.Types;
import java.util.List;

@Component
public class PersonDAO {
    /*
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT id, name, birth_year FROM person WHERE id =?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public List<Person> getAll(){
        return jdbcTemplate.query("SELECT id, name, birth_year FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void create(Person person){
        jdbcTemplate.update("INSERT INTO person (name, birth_year) VALUES (?,?)",
                new Object[]{person.getName(), person.getBirthYear()},
                new int[]{Types.VARCHAR, Types.INTEGER});
    }
    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name=?, birth_year=? WHERE id =?",
                new Object[]{person.getName(), person.getBirthYear(), person.getId()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
    }

    public void delete(int id)
    {
        jdbcTemplate.update("DELETE FROM person WHERE id =?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

     */
}
