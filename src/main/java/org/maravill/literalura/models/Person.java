package org.maravill.literalura.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer birthYear;
    private Integer deathYear;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<Book> authoredBooks;

    @JsonIgnore
    @ManyToMany(mappedBy = "translators")
    private List<Book> translatedBooks;

    public Person() {
    }

    public Person(Long id, Integer birthYear, Integer deathYear, String name) {
        this.id = id;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getAuthoredBooks() {
        return authoredBooks;
    }

    public void setAuthoredBooks(List<Book> authoredBooks) {
        this.authoredBooks = authoredBooks;
    }

    public List<Book> getTranslatedBooks() {
        return translatedBooks;
    }

    public void setTranslatedBooks(List<Book> translatedBooks) {
        this.translatedBooks = translatedBooks;
    }
}
