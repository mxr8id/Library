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
public class BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> index() {
        List<Book> books = new ArrayList<>();
        books = jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
        return books;
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) values(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
        Book book = null;
        book = jdbcTemplate.queryForObject("select * from book where id = ?", new BeanPropertyRowMapper<>(Book.class), id);
        return book;
    }

    public Person showOwner(int id) {
        Person person = null;
        person = jdbcTemplate.queryForObject("select p.id, p.name, p.surname, p.patronymic, p.age from book b left join person p on p.id=b.person_id where b.id = ?", new BeanPropertyRowMapper<>(Person.class), id);
        return person;
    }

    public void toFreeBook(int id) {
        jdbcTemplate.update("update book set person_id=null where id=?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?",
                book.getName(), book.getAuthor(), book.getYear(), book.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public void addBook(int peopleId, int id) {
        jdbcTemplate.update("update book set person_id=? where id = ?", peopleId, id);
    }
}
