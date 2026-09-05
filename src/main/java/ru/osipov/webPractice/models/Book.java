package ru.osipov.webPractice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 150, message = "name should be between 2 and 150 chars")
    private String name;
    @NotEmpty(message = "author should not be empty")
    private String author;
    @Min(value = 0, message = "year should be greater than 0")
    private Integer year;
    @Column(name = "time_book_busy")
    private LocalDateTime TimeBookBusy;
    @Transient
    private boolean isBookExpired;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Book(Integer id, String name, String author, Integer year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getTimeBookBusy() {
        return TimeBookBusy;
    }

    public void setTimeBookBusy(LocalDateTime timeBookBusy) {
        TimeBookBusy = timeBookBusy;
    }

    public boolean isBookExpired() {
        return isBookExpired;
    }

    public void setBookExpired(boolean bookExpired) {
        isBookExpired = bookExpired;
    }

    @Override
    public String toString() {
        return name + ", " + author + ", " + year;
    }
}
