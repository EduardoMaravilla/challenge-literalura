package org.maravill.literalura.services;

import org.maravill.literalura.dto.BookDto;

import java.util.List;

public interface IBooksApiService {
    List<BookDto> getBooksByTitle(String title);

    List<BookDto> searchTop10Books();
}
