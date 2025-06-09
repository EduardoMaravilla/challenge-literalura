package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISummaryRepository extends JpaRepository<Summary,Long> {
}
