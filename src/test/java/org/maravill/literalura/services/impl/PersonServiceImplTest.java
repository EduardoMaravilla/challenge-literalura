package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.Person;
import org.maravill.literalura.repositories.IPersonRepository;
import org.maravill.literalura.services.MapperService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {
    private IPersonRepository personRepository;
    private MapperService mapperService;
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personRepository = mock(IPersonRepository.class);
        mapperService = mock(MapperService.class);
        personService = new PersonServiceImpl(personRepository, mapperService);
    }

    @Test
    void testFindOrCreatePersons_emptyList() {
        assertEquals(Collections.emptyList(), personService.findOrCreatePersons(Collections.emptyList()));
    }

    @Test
    void testFindOrCreatePersons_success() {
        Person person = new Person(null, 1900, 1950, "Autor");
        when(personRepository.findByName("Autor")).thenReturn(Optional.empty());
        when(personRepository.save(person)).thenReturn(person);
        assertEquals(Collections.singletonList(person), personService.findOrCreatePersons(Collections.singletonList(person)));
    }

    @Test
    void testFindOrCreatePersons_existing() {
        Person person = new Person(null, 1900, 1950, "Autor");
        when(personRepository.findByName("Autor")).thenReturn(Optional.of(person));
        assertEquals(Collections.singletonList(person), personService.findOrCreatePersons(Collections.singletonList(person)));
        verify(personRepository, never()).save(any());
    }

    @Test
    void testFindOrCreatePersons_invalidName() {
        Person person = new Person(null, 1900, 1950, "");
        assertThrows(IllegalArgumentException.class, () -> personService.findOrCreatePersons(Collections.singletonList(person)));
    }

    @Test
    void testGetAllAuthors() {
        Person person = new Person(null, 1900, 1950, "Autor");
        PersonDto dto = new PersonDto(1900, 1950, "Autor");
        when(personRepository.findAll()).thenReturn(List.of(person));
        when(mapperService.convertToDto(person)).thenReturn(dto);
        assertEquals(List.of(dto), personService.getAllAuthors());
    }

    @Test
    void testGetLivingAuthorsInYear() {
        Person person = new Person(null, 1900, null, "Autor");
        PersonDto dto = new PersonDto(1900, null, "Autor");
        when(personRepository.findAliveOrPossiblyAliveInYear(2000)).thenReturn(List.of(person));
        when(mapperService.convertToDto(person)).thenReturn(dto);
        assertEquals(List.of(dto), personService.getLivingAuthorsInYear(2000));
    }
} 