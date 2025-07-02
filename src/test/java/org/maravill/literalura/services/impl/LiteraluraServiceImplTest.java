package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.config.LiteraluraShutdownHook;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.services.*;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LiteraluraServiceImplTest {
    private LiteraluraShutdownHook shutdownHook;
    private IBooksApiService booksApiService;
    private IBookService bookService;
    private IPersonService personService;
    private ISearchTitleHistoryService searchTitleHistoryService;
    private LiteraluraServiceImpl service;

    @BeforeEach
    void setUp() {
        shutdownHook = mock(LiteraluraShutdownHook.class);
        booksApiService = mock(IBooksApiService.class);
        bookService = mock(IBookService.class);
        personService = mock(IPersonService.class);
        searchTitleHistoryService = mock(ISearchTitleHistoryService.class);
        service = new LiteraluraServiceImpl(shutdownHook, booksApiService, bookService, personService, searchTitleHistoryService);
    }

    @Test
    void testShowMainMenu() {
        String menu = service.showMainMenu();
        assertTrue(menu.contains("MENÚ PRINCIPAL"));
    }

    @Test
    void testSearchBookByTitle_foundInHistory() {
        when(searchTitleHistoryService.isTitleInHistory("titulo")).thenReturn(true);
        BookDto book = new BookDto(1L, "titulo", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Collections.emptyMap(), 1);
        when(bookService.findBooksByTitle("titulo")).thenReturn(List.of(book));
        String result = service.searchBookByTitle("titulo");
        assertTrue(result.contains("Libros encontrados"));
    }

    @Test
    void testSearchBookByTitle_notFoundInHistory() {
        when(searchTitleHistoryService.isTitleInHistory("titulo")).thenReturn(false);
        BookDto book = new BookDto(1L, "titulo", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Collections.emptyMap(), 1);
        when(booksApiService.getBooksByTitle("titulo")).thenReturn(List.of(book));
        doNothing().when(bookService).saveBooks(anyList());
        String result = service.searchBookByTitle("titulo");
        assertTrue(result.contains("Libros encontrados"));
        verify(searchTitleHistoryService).saveSearchTitleHistory("titulo");
        verify(bookService).saveBooks(anyList());
    }

    @Test
    void testSearchBookByTitle_noResults() {
        when(searchTitleHistoryService.isTitleInHistory("titulo")).thenReturn(true);
        when(bookService.findBooksByTitle("titulo")).thenReturn(Collections.emptyList());
        String result = service.searchBookByTitle("titulo");
        assertTrue(result.contains("No se encontraron libros"));
    }

    @Test
    void testListRegisteredBooks_empty() {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        String result = service.listRegisteredBooks();
        assertTrue(result.contains("No hay libros registrados"));
    }

    @Test
    void testListRegisteredBooks_withBooks() {
        BookDto book = new BookDto(1L, "titulo", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Collections.emptyMap(), 1);
        when(bookService.getAllBooks()).thenReturn(List.of(book));
        String result = service.listRegisteredBooks();
        assertTrue(result.contains("Libros registrados"));
    }

    @Test
    void testListRegisteredAuthors_empty() {
        when(personService.getAllAuthors()).thenReturn(Collections.emptyList());
        String result = service.listRegisteredAuthors();
        assertTrue(result.contains("No hay autores registrados"));
    }

    @Test
    void testListRegisteredAuthors_withAuthors() {
        PersonDto author = new PersonDto(1900, 1950, "Autor");
        when(personService.getAllAuthors()).thenReturn(List.of(author));
        String result = service.listRegisteredAuthors();
        assertTrue(result.contains("Autores registrados"));
    }

    @Test
    void testListLivingAuthorsInYear_empty() {
        when(personService.getLivingAuthorsInYear(2000)).thenReturn(Collections.emptyList());
        String result = service.listLivingAuthorsInYear(2000);
        assertTrue(result.contains("No hay autores vivos"));
    }

    @Test
    void testListLivingAuthorsInYear_withAuthors() {
        PersonDto author = new PersonDto(1900, null, "Autor");
        when(personService.getLivingAuthorsInYear(2000)).thenReturn(List.of(author));
        String result = service.listLivingAuthorsInYear(2000);
        assertTrue(result.contains("Autores vivos en el año"));
    }

    // Métodos de paginación, ordenamiento, ayuda, etc. pueden ser cubiertos con tests similares
    // Aquí se muestra un ejemplo para listBooksByLanguage

    @Test
    void testListBooksByLanguage_empty() {
        when(bookService.getBooksByLanguage("es")).thenReturn(Collections.emptyList());
        String result = service.listBooksByLanguage("es");
        assertTrue(result.contains("No hay libros registrados en el idioma"));
    }

    @Test
    void testListBooksByLanguage_withBooks() {
        BookDto book = new BookDto(1L, "titulo", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Collections.emptyMap(), 1);
        when(bookService.getBooksByLanguage("es")).thenReturn(List.of(book));
        String result = service.listBooksByLanguage("es");
        assertTrue(result.contains("Libros registrados en el idioma"));
    }

    // Puedes continuar agregando tests para los demás métodos públicos siguiendo este patrón
} 