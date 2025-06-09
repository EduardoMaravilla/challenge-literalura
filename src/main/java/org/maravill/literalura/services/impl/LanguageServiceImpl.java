package org.maravill.literalura.services.impl;

import org.maravill.literalura.models.Language;
import org.maravill.literalura.repositories.ILanguageRepository;
import org.maravill.literalura.services.ILanguageService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LanguageServiceImpl implements ILanguageService {

    private final ILanguageRepository languageRepository;

    public LanguageServiceImpl(ILanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findOrCreateLanguages(List<Language> languages) {
        if (languages == null || languages.isEmpty()) {
            return Collections.emptyList();
        }
        return languages.stream()
                .map(this::findOrCreateLanguage)
                .toList();
    }


    private Language findOrCreateLanguage(Language language) {
        if (language.getName() == null || language.getName().isBlank()) {
            throw new IllegalArgumentException("el nombre del idioma no puede ser nulo o vacío");
        }
        return languageRepository.findByName(language.getName())
                .orElseGet(() -> languageRepository.save(language));
    }


}
