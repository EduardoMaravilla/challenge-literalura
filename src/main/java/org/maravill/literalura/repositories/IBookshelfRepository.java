package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookshelfRepository extends JpaRepository<Bookshelf,Long> {
    Optional<Bookshelf> findByName(String name);
}
