package org.maravill.literalura.services;

public interface ILiteraluraService {

    String showMainMenu();

    String searchBookByTitle(String title);

    String listRegisteredBooks();

    String listRegisteredAuthors();

    String listLivingAuthorsInYear(int year);

    String listBooksByLanguage(String language);

    void exit();

    String listBooksByAuthor(String authorString);

    String listTop10Books();

    String localStatistics();

    String listPageBooks(String args);
    String listNextBooks();
    String listPreviousBooks();
    String listPageAuthors(String args);
    String listNextAuthors();
    String listPreviousAuthors();

    String getCurrentPageBooksHelp();

    String getCurrentPageAuhtorsHelp();
}
