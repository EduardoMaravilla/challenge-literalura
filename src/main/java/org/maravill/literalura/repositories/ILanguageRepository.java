package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILanguageRepository extends JpaRepository<Language,Long> {
    Optional<Language> findByName(String name);
}
