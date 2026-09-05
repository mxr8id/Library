package ru.osipov.webPractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osipov.webPractice.models.Book;
import ru.osipov.webPractice.models.Person;
import ru.osipov.webPractice.repositories.BookRepository;
import ru.osipov.webPractice.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> indexWithoutPaging(Integer page, Integer books_per_page, boolean sort_by_year) {
        if (sort_by_year) {
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }
    }

    public List<Book> indexWithPaging(Integer page, Integer books_per_page, boolean sort_by_year) {
        if (sort_by_year) {
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        }

    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book show(int id) {
        return bookRepository.findById(id).get();
    }

    public Person showOwner(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.get();
        return book.getPerson();
    }

    @Transactional
    public void toFreeBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.get();
        book.setPerson(null);
        book.setTimeBookBusy(null);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToBeUpdated = bookRepository.findById(id).get();
        book.setId(id);
        book.setPerson(bookToBeUpdated.getPerson());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addBook(int peopleId, int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга с id " + id + " не найдена"));

        Person person = personRepository.findById(peopleId)
                .orElseThrow(() -> new IllegalArgumentException("Человек с id " + peopleId + " не найден"));
        book.setPerson(person);
        book.setTimeBookBusy(LocalDateTime.now());
    }

    public List<Book> search(String query) {
        return bookRepository.findByNameStartingWith(query);
    }

}
