package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject,Long> {
    Optional<Subject> findByName(String name);
}
