package ru.osipov.webPractice.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osipov.webPractice.models.Book;
import ru.osipov.webPractice.models.Person;
import ru.osipov.webPractice.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public Person show(int id) {
        return personRepository.findById(id).get();
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> showAllBooks(int personId) {
        Optional<Person> person = personRepository.findById(personId);
        Hibernate.initialize(person.get().getBooks());
        List<Book> books = person.get().getBooks();
        LocalDateTime checkDate = LocalDateTime.now();
        for (Book book : books) {
            book.setBookExpired(!(checkDate.isAfter(book.getTimeBookBusy())
                    && checkDate.isBefore(book.getTimeBookBusy().plusDays(10))));
        }
        return books;
    }

    public List<Person> search(String name) {
        return personRepository.findByNameStartingWith(name);
    }

}
