package org.maravill.literalura.services.impl;

import org.maravill.literalura.models.Bookshelf;
import org.maravill.literalura.repositories.IBookshelfRepository;
import org.maravill.literalura.services.IBookshelfService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookshelfServiceImpl implements IBookshelfService {

    private final IBookshelfRepository bookshelfRepository;

    public BookshelfServiceImpl(IBookshelfRepository bookshelfRepository) {
        this.bookshelfRepository = bookshelfRepository;
    }

    @Override
    public List<Bookshelf> findOrCreateBookshelves(List<Bookshelf> bookshelves) {
        if (bookshelves == null || bookshelves.isEmpty()) {
            return Collections.emptyList();
        }
        return bookshelves.stream()
                .map(this::findOrCreateBookshelf)
                .toList();
    }

    private Bookshelf findOrCreateBookshelf(Bookshelf bookshelf) {
        if (bookshelf.getName() == null || bookshelf.getName().isBlank()) {
            throw new IllegalArgumentException("el nombre de la estantería no puede ser nulo o vacío");
        }
        return bookshelfRepository.findByName(bookshelf.getName())
                .orElseGet(() -> bookshelfRepository.save(bookshelf));
    }
}
