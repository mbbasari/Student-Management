package com.project.repository.business;

import com.project.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsLessonByLessonNameEqualsIgnoreCase(String lessonName);

}
