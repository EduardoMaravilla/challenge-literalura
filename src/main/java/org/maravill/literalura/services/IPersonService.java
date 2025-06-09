package org.maravill.literalura.services;

import org.maravill.literalura.dto.PersonDto;
import org.maravill.literalura.models.Person;

import java.util.List;

public interface IPersonService {

    List<Person> findOrCreatePersons(List<Person> persons);

    List<PersonDto> getAllAuthors();

    List<PersonDto> getLivingAuthorsInYear(int year);

}
