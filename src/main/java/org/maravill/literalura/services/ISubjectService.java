package org.maravill.literalura.services;

import org.maravill.literalura.models.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> findOrCreateSubjects(List<Subject> subjects);
}
