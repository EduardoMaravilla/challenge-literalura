package org.maravill.literalura.services;


import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapperService {

    public Person convertToEntity(PersonDto personDto) {
        if (personDto == null) {
            return null;
        }
        Person person = new Person();
        person.setBirthYear(personDto.birthYear());
        person.setDeathYear(personDto.deathYear());
        person.setName(personDto.name());
        return person;
    }

    public PersonDto convertToDto(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonDto(
                person.getBirthYear(),
                person.getDeathYear(),
                person.getName()
        );
    }

    public Format convertToEntity(String mimeType, String url) {
        if (mimeType == null || url == null) {
            return null;
        }
        Format format = new Format();
        format.setMimeType(mimeType);
        format.setUrl(url);
        return format;
    }

    public Map<String,String> convertToDto(List<Format> formats){
        if (formats == null || formats.isEmpty()) {
            return Map.of();
        }
        return formats.stream()
                .collect(Collectors.toMap(Format::getMimeType, Format::getUrl));
    }

    public BookDto convertToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDto(
                book.getIdGutenberg(),
                book.getTitle(),
                book.getAuthors().stream().map(this::convertToDto).toList(),
                book.getSummaries().stream().map(Summary::getName).toList(),
                book.getTranslators().stream().map(this::convertToDto).toList(),
                book.getSubjects().stream().map(Subject::getName).toList(),
                book.getBookshelves().stream().map(Bookshelf::getName).toList(),
                book.getLanguages().stream().map(Language::getName).toList(),
                book.getCopyright(),
                book.getMediaType(),
                convertToDto(book.getFormats()),
                book.getDownloadCount()
        );
    }
}
