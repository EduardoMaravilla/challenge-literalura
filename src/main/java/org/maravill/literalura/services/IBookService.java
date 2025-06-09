package org.maravill.literalura.services;

import org.maravill.literalura.dto.BookDto;

import java.util.List;

public interface IBookService {
    void saveBooks(List<BookDto> books);

    List<BookDto> getAllBooks();

    List<BookDto> getBooksByLanguage(String language);

    List<BookDto> getBooksByAuthor(String name);

    List<BookDto> findBooksByTitle(String title);
}
