package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.entity.concretes.business.Lesson;
import com.project.entity.concretes.business.LessonProgram;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.LessonProgramMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.LessonProgramRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.LessonProgramResponse;
import com.project.repository.business.LessonProgramRepository;
import com.project.service.validator.DateTimeValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final DateTimeValidator dateTimeValidator;
    private final LessonProgramMapper lessonProgramMapper;


    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {
       Set<Lesson> lessons= lessonService.getAllLessonByLessonId(lessonProgramRequest.getLessonIdList());
      EducationTerm educationTerm = educationTermService.getAllEducationTermsById(lessonProgramRequest.getEducationTermId());

        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_IN_LIST);
        }

dateTimeValidator.checkTimeWithException(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());
        LessonProgram lessonProgram = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(lessonProgramRequest, lessons, educationTerm);
        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
        return ResponseMessage.<LessonProgramResponse>builder()
                .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                .status(HttpStatus.CREATED)
                .object(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                .build();


    }
}
