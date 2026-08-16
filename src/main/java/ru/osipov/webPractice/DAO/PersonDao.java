package ru.osipov.webPractice.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.osipov.webPractice.models.Book;
import ru.osipov.webPractice.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        people = jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
        return people;
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, surname, patronymic, age) values(?, ?, ?, ?)", person.getName(), person.getSurname(), person.getPatronymic(), person.getAge());
    }

    public Person show(int id) {
        Person person = null;
        person = jdbcTemplate.queryForObject("select * from person where id = ?", new BeanPropertyRowMapper<>(Person.class), id);
        return person;
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set name=?, surname=?, patronymic=?, age=? where id=?",
                person.getName(), person.getSurname(), person.getPatronymic(), person.getAge(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> showAllBooks(int personId) {
        List<Book> books = new ArrayList<>();
        books = jdbcTemplate.query("select b.name, b.author, b.year from book b " +
                "where b.person_id = ?", new BeanPropertyRowMapper<>(Book.class), personId);
        return books;
    }
}
