package org.maravill.literalura.services;

public interface ISearchTitleHistoryService {

    boolean isTitleInHistory(String title);

    void saveSearchTitleHistory(String title);
}
