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
}
