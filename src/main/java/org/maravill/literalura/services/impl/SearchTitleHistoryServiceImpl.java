package org.maravill.literalura.services.impl;

import org.maravill.literalura.models.SearchTitleHistory;
import org.maravill.literalura.repositories.ISearchTitleHistoryRepository;
import org.maravill.literalura.services.ISearchTitleHistoryService;
import org.springframework.stereotype.Service;

@Service
public class SearchTitleHistoryServiceImpl implements ISearchTitleHistoryService {

    private final ISearchTitleHistoryRepository searchTitleHistoryRepository;

    public SearchTitleHistoryServiceImpl(ISearchTitleHistoryRepository searchTitleHistoryRepository) {
        this.searchTitleHistoryRepository = searchTitleHistoryRepository;
    }

    @Override
    public boolean isTitleInHistory(String title) {
        return searchTitleHistoryRepository.existsByTitle(title);
    }

    @Override
    public void saveSearchTitleHistory(String title) {
        searchTitleHistoryRepository.save(new SearchTitleHistory(title));
    }
}
