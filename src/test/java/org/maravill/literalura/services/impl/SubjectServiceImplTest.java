package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.models.Subject;
import org.maravill.literalura.repositories.ISubjectRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {
    private ISubjectRepository subjectRepository;
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        subjectRepository = mock(ISubjectRepository.class);
        subjectService = new SubjectServiceImpl(subjectRepository);
    }

    @Test
    void testFindOrCreateSubjects_emptyList() {
        assertEquals(Collections.emptyList(), subjectService.findOrCreateSubjects(Collections.emptyList()));
    }

    @Test
    void testFindOrCreateSubjects_success() {
        Subject subject = new Subject("Ciencia");
        when(subjectRepository.findByName("Ciencia")).thenReturn(Optional.empty());
        when(subjectRepository.save(subject)).thenReturn(subject);
        assertEquals(Collections.singletonList(subject), subjectService.findOrCreateSubjects(Collections.singletonList(subject)));
    }

    @Test
    void testFindOrCreateSubjects_existing() {
        Subject subject = new Subject("Arte");
        when(subjectRepository.findByName("Arte")).thenReturn(Optional.of(subject));
        assertEquals(Collections.singletonList(subject), subjectService.findOrCreateSubjects(Collections.singletonList(subject)));
        verify(subjectRepository, never()).save(any());
    }

    @Test
    void testFindOrCreateSubjects_invalidName() {
        Subject subject = new Subject("");
        assertThrows(IllegalArgumentException.class, () -> subjectService.findOrCreateSubjects(Collections.singletonList(subject)));
    }
} 