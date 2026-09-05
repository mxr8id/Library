package ru.osipov.webPractice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 2, max = 150, message = "name should be between 2 and 150 chars")
    private String name;
    @Size(min = 2, max = 150, message = "surname should be between 2 and 150 chars")
    private String surname;
    @Size(min = 2, max = 150, message = "patronymic should be between 2 and 150 chars")
    private String patronymic;
    @Min(value = 0, message = "age should be greater than 0")
    private Integer age;
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Book> books;

    public Person(Integer id, String name, String surname, String patronymic, Integer age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }


    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + patronymic + ", " + age;
    }
}
