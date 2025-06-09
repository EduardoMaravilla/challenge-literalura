package org.maravill.literalura.repositories;

import org.maravill.literalura.models.SearchTitleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISearchTitleHistoryRepository extends JpaRepository<SearchTitleHistory,Long> {
    boolean existsByTitle(String title);
}
