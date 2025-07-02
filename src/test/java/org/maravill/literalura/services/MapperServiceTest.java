package org.maravill.literalura.services;

import org.junit.jupiter.api.Test;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.*;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class MapperServiceTest {
    private final MapperService mapper = new MapperService();

    @Test
    void testConvertToEntity_PersonDto() {
        PersonDto dto = new PersonDto(1900, 1950, "Autor");
        Person person = mapper.convertToEntity(dto);
        assertEquals("Autor", person.getName());
        assertEquals(1900, person.getBirthYear());
        assertEquals(1950, person.getDeathYear());
    }

    @Test
    void testConvertToEntity_PersonDto_null() {
        assertNull(mapper.convertToEntity(null));
    }

    @Test
    void testConvertToDto_Person() {
        Person person = new Person(null, 1900, 1950, "Autor");
        PersonDto dto = mapper.convertToDto(person);
        assertEquals("Autor", dto.name());
        assertEquals(1900, dto.birthYear());
        assertEquals(1950, dto.deathYear());
    }

    @Test
    void testConvertToEntity_Format() {
        Format format = mapper.convertToEntity("text/plain", "url");
        assertEquals("text/plain", format.getMimeType());
        assertEquals("url", format.getUrl());
    }

    @Test
    void testConvertToEntity_Format_null() {
        assertNull(mapper.convertToEntity(null, null));
    }

    @Test
    void testConvertToDto_Formats() {
        Format format = new Format("text/plain", "url");
        Map<String, String> map = mapper.convertToDto(List.of(format));
        assertEquals("url", map.get("text/plain"));
    }

    @Test
    void testConvertToDto_Formats_empty() {
        assertTrue(mapper.convertToDto(List.of()).isEmpty());
    }

    @Test
    void testConvertToDto_Book() {
        Book book = new Book(1L, "Titulo", List.of(new Person()), List.of(), List.of(new Subject("Tema")), List.of(new Bookshelf("Estante")), List.of(new Language("es")), true, "media", 10);
        book.setSummaries(List.of(new Summary("Resumen")));
        book.setFormats(List.of(new Format("text/plain", "url")));
        BookDto dto = mapper.convertToDto(book);
        assertEquals("Titulo", dto.title());
        assertEquals(1L, dto.id());
        assertEquals(List.of("Tema"), dto.subjects());
        assertEquals(List.of("Estante"), dto.bookshelves());
        assertEquals(List.of("es"), dto.languages());
        assertEquals(List.of("Resumen"), dto.sumaries());
        assertEquals("url", dto.formats().get("text/plain"));
    }

    @Test
    void testConvertToDto_Book_null() {
        assertNull(mapper.convertToDto((Book) null));
    }
} 