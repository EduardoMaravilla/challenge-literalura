package org.maravill.literalura.services;

import org.maravill.literalura.models.Language;

import java.util.List;

public interface ILanguageService {
    List<Language> findOrCreateLanguages(List<Language> languages);
}
