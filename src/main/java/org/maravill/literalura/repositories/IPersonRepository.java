package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p WHERE " +
            "(p.birthYear IS NOT NULL OR p.deathYear IS NOT NULL) AND " +
            "(" +
            "(p.birthYear IS NOT NULL AND p.deathYear IS NOT NULL AND p.birthYear <= :year AND p.deathYear >= :year) OR " +
            "(p.birthYear IS NOT NULL AND p.deathYear IS NULL AND p.birthYear <= :year AND (p.birthYear+50) >= :year) OR " +
            "(p.birthYear IS NULL AND p.deathYear IS NOT NULL AND (p.deathYear - 50) <= :year AND p.deathYear >= :year)" +
            ")")
    List<Person> findAliveOrPossiblyAliveInYear(@Param("year") int year);

}
