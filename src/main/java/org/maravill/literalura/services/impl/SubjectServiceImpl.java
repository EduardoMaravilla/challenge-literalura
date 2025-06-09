package org.maravill.literalura.services.impl;


import org.maravill.literalura.models.Subject;
import org.maravill.literalura.repositories.ISubjectRepository;
import org.maravill.literalura.services.ISubjectService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SubjectServiceImpl implements ISubjectService {

    private final ISubjectRepository subjectRepository;

    public SubjectServiceImpl(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findOrCreateSubjects(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return Collections.emptyList();
        }
        return subjects.stream()
                .map(this::findOrCreateSubject)
                .toList();
    }

    private Subject findOrCreateSubject(Subject subject) {
        if (subject.getName() == null || subject.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del tema no puede ser nulo o vacío");
        }
        return subjectRepository.findByName(subject.getName())
                .orElseGet(() -> subjectRepository.save(subject));
    }
}
