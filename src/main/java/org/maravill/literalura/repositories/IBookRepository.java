package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIdGutenberg(Long id);

    @Query("SELECT b FROM Book b JOIN b.languages l WHERE l.name = :language")
    List<Book> findByLanguagesName(String language);

    List<Book> findByAuthors_NameContainingIgnoreCase(String name);

    List<Book> findAllByTitleContainingIgnoreCase(String title);
}
