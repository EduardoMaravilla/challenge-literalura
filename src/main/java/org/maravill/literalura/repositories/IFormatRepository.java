package org.maravill.literalura.repositories;

import org.maravill.literalura.models.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormatRepository extends JpaRepository<Format,Long> {
}
