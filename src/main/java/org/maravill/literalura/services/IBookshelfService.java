package org.maravill.literalura.services;

import org.maravill.literalura.models.Bookshelf;

import java.util.List;

public interface IBookshelfService {

    List<Bookshelf> findOrCreateBookshelves(List<Bookshelf> bookshelves);
}
