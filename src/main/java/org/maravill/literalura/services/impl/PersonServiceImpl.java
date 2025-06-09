package org.maravill.literalura.services.impl;

import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.Person;
import org.maravill.literalura.repositories.IPersonRepository;
import org.maravill.literalura.services.IPersonService;
import org.maravill.literalura.services.MapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    private final IPersonRepository personRepository;
    private final MapperService mapperService;

    public PersonServiceImpl(IPersonRepository personRepository,
                             MapperService mapperService) {
        this.personRepository = personRepository;
        this.mapperService = mapperService;
    }

    @Override
    public List<Person> findOrCreatePersons(List<Person> persons) {
        if (persons == null || persons.isEmpty()) {
            return Collections.emptyList();
        }
        return persons.stream()
                .map(this::findOrCreatePerson)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonDto> getAllAuthors() {
        return personRepository.findAll().stream().map(mapperService::convertToDto).toList();
    }

    @Override
    public List<PersonDto> getLivingAuthorsInYear(int year) {
        return personRepository.findAliveOrPossiblyAliveInYear(year).stream().map(mapperService::convertToDto).toList();
    }



    private Person findOrCreatePerson(Person person) {
        if (person.getName() == null || person.getName().isBlank()) {
            throw new IllegalArgumentException("el nombre del autor/traductor no puede ser nulo o vacío");
        }
        return personRepository.findByName(person.getName())
                .orElseGet(() -> personRepository.save(person));
    }
}
