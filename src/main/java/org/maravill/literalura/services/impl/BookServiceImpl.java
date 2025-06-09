package org.maravill.literalura.services.impl;

import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.models.*;
import org.maravill.literalura.repositories.IBookRepository;
import org.maravill.literalura.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    private final IBookRepository bookRepository;
    private final IPersonService personService;
    private final ISubjectService subjectService;
    private final MapperService mapperService;
    private final IBookshelfService bookshelfService;
    private final ILanguageService languageService;

    public BookServiceImpl(IBookRepository bookRepository
            , IPersonService personService, MapperService mapperService
            , ISubjectService subjectService, IBookshelfService bookshelfService
            , ILanguageService languageService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
        this.mapperService = mapperService;
        this.subjectService = subjectService;
        this.bookshelfService = bookshelfService;
        this.languageService = languageService;
    }

    @Override
    @Transactional
    public void saveBooks(List<BookDto> books) {
        if (books == null || books.isEmpty()) {
            return;
        }
        for (BookDto bookDto : books) {
            saveBook(bookDto);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            return books.stream()
                    .map(mapperService::convertToDto)
                    .toList();
        }
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getBooksByLanguage(String language) {
        return bookRepository.findByLanguagesName(language).stream()
                .map(mapperService::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getBooksByAuthor(String name) {
        List<Book> books = bookRepository.findByAuthors_NameContainingIgnoreCase(name);
        if (!books.isEmpty()) {
            return books.stream()
                    .map(mapperService::convertToDto)
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<BookDto> findBooksByTitle(String title) {
        return bookRepository.findAllByTitleContainingIgnoreCase(title).stream()
                .map(mapperService::convertToDto)
                .toList();
    }


    private void saveBook(BookDto bookDto) {
        List<Person> authors = personService.findOrCreatePersons(bookDto.authors().stream().map(mapperService::convertToEntity).toList());
        List<Person> translators = personService.findOrCreatePersons(bookDto.translators().stream().map(mapperService::convertToEntity).toList());

        List<Summary> summaries = new ArrayList<>();
        if (bookDto.sumaries() != null) {
            summaries = bookDto.sumaries().stream().map(Summary::new).toList();
        }
        List<Subject> subjects = subjectService.findOrCreateSubjects(bookDto.subjects().stream().map(Subject::new).toList());
        List<Bookshelf> bookshelves = bookshelfService.findOrCreateBookshelves(bookDto.bookshelves().stream().map(Bookshelf::new).toList());
        List<Language> languages = languageService.findOrCreateLanguages(bookDto.languages().stream().map(Language::new).toList());

        List<Format> formats = new ArrayList<>();
        if (bookDto.formats() != null) {
            bookDto.formats().forEach((mimeType, url) -> {
                Format format = mapperService.convertToEntity(mimeType, url);
                if (format != null) {
                    formats.add(format);
                }
            });
        }


        Book book = new Book(
                bookDto.id(),
                bookDto.title(),
                authors,
                translators,
                subjects,
                bookshelves,
                languages,
                bookDto.copyright(),
                bookDto.mediaType(),
                bookDto.downloadCount()
        );
        book.setSummaries(summaries);
        book.setFormats(formats);

        bookRepository.findByIdGutenberg(bookDto.id()).ifPresentOrElse(book1 -> {
        }, () -> bookRepository.save(book));

    }

}
