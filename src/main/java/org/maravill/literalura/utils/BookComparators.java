package org.maravill.literalura.utils;

import org.maravill.literalura.dto.BookDto;

import java.util.Comparator;

public final class BookComparators {

    private BookComparators() {
    }

    // Ordenar por título (A → Z)
    public static final Comparator<BookDto> TITLE_ASC = Comparator.comparing(
            book -> book.title() == null ? "" : book.title().toLowerCase()
    );

    // Ordenar por título (Z → A)
    public static final Comparator<BookDto> TITLE_DESC = Comparator.comparing(
            book -> book.title() == null ? "" : book.title().toLowerCase(),
            Comparator.reverseOrder()
    );

    // Ordenar por nombre del primer autor (A → Z)
    public static final Comparator<BookDto> AUTHOR_ASC = Comparator.comparing(
            book -> book.authors().isEmpty() ? "" : book.authors().getFirst().name().toLowerCase()
    );

    // Ordenar por nombre del primer autor (Z → A)
    public static final Comparator<BookDto> AUTHOR_DESC = Comparator.comparing(
            book -> book.authors().isEmpty() ? "" : book.authors().getFirst().name().toLowerCase(),
            Comparator.reverseOrder()
    );

    // Ordenar por número de descargas (menor → mayor)
    public static final Comparator<BookDto> DOWNLOAD_ASC = Comparator.comparing(
            book -> book.downloadCount() == null ? 0 : book.downloadCount()
    );

    // Ordenar por número de descargas (mayor → menor)
    public static final Comparator<BookDto> DOWNLOAD_DESC = Comparator.comparing(
            book -> book.downloadCount() == null ? 0 : book.downloadCount(),
            Comparator.reverseOrder()
    );

    // Ordenar por disponibilidad de copyright (con copyright primero)
    public static final Comparator<BookDto> COPYRIGHT_FIRST = Comparator.comparing(
            book -> book.copyright() != null && !book.copyright()
    );

    // Ordenar por dominio público primero
    public static final Comparator<BookDto> PUBLIC_DOMAIN_FIRST = Comparator.comparing(
            book -> book.copyright() != null && book.copyright()
    );
}

