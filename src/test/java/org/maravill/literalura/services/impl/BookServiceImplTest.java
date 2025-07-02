package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.*;
import org.maravill.literalura.repositories.IBookRepository;
import org.maravill.literalura.services.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private IBookRepository bookRepository;
    private IPersonService personService;
    private ISubjectService subjectService;
    private MapperService mapperService;
    private IBookshelfService bookshelfService;
    private ILanguageService languageService;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(IBookRepository.class);
        personService = mock(IPersonService.class);
        subjectService = mock(ISubjectService.class);
        mapperService = mock(MapperService.class);
        bookshelfService = mock(IBookshelfService.class);
        languageService = mock(ILanguageService.class);
        bookService = new BookServiceImpl(bookRepository, personService, mapperService, subjectService, bookshelfService, languageService);
    }

    @Test
    void testSaveBooks_nullOrEmpty() {
        bookService.saveBooks(null);
        bookService.saveBooks(Collections.emptyList());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testSaveBooks_success() {
        BookDto dto = new BookDto(1L, "Titulo", List.of(new PersonDto(1900, 1950, "Autor")), List.of("Resumen"), List.of(), List.of("Tema"), List.of("Estante"), List.of("es"), true, "media", Map.of("text/plain", "url"), 10);
        Book book = mock(Book.class);
        when(mapperService.convertToEntity(any(PersonDto.class))).thenReturn(new Person());
        when(personService.findOrCreatePersons(anyList())).thenReturn(List.of(new Person()));
        when(subjectService.findOrCreateSubjects(anyList())).thenReturn(List.of(new Subject("Tema")));
        when(bookshelfService.findOrCreateBookshelves(anyList())).thenReturn(List.of(new Bookshelf("Estante")));
        when(languageService.findOrCreateLanguages(anyList())).thenReturn(List.of(new Language("es")));
        when(mapperService.convertToEntity(anyString(), anyString())).thenReturn(new Format("text/plain", "url"));
        when(bookRepository.findByIdGutenberg(1L)).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.saveBooks(List.of(dto));
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testGetAllBooks_empty() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookService.getAllBooks());
    }

    @Test
    void testGetAllBooks_withBooks() {
        Book book = mock(Book.class);
        BookDto dto = mock(BookDto.class);
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(mapperService.convertToDto(book)).thenReturn(dto);
        assertEquals(List.of(dto), bookService.getAllBooks());
    }

    @Test
    void testGetBooksByLanguage() {
        Book book = mock(Book.class);
        BookDto dto = mock(BookDto.class);
        when(bookRepository.findByLanguagesName("es")).thenReturn(List.of(book));
        when(mapperService.convertToDto(book)).thenReturn(dto);
        assertEquals(List.of(dto), bookService.getBooksByLanguage("es"));
    }

    @Test
    void testGetBooksByAuthor_empty() {
        when(bookRepository.findByAuthors_NameContainingIgnoreCase("Autor")).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookService.getBooksByAuthor("Autor"));
    }

    @Test
    void testGetBooksByAuthor_withBooks() {
        Book book = mock(Book.class);
        BookDto dto = mock(BookDto.class);
        when(bookRepository.findByAuthors_NameContainingIgnoreCase("Autor")).thenReturn(List.of(book));
        when(mapperService.convertToDto(book)).thenReturn(dto);
        assertEquals(List.of(dto), bookService.getBooksByAuthor("Autor"));
    }

    @Test
    void testFindBooksByTitle() {
        Book book = mock(Book.class);
        BookDto dto = mock(BookDto.class);
        when(bookRepository.findAllByTitleContainingIgnoreCase("Titulo")).thenReturn(List.of(book));
        when(mapperService.convertToDto(book)).thenReturn(dto);
        assertEquals(List.of(dto), bookService.findBooksByTitle("Titulo"));
    }
} 