package com.project.service.business;

import com.project.entity.concretes.business.Lesson;
import com.project.exception.ConflictException;
import com.project.payload.mappers.LessonMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.LessonResponse;
import com.project.repository.business.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LessonService {

    private final LessonRepository lessonRepository;;
    private final LessonMapper lessonMapper;

    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
        isLessonExistByLessonName(lessonRequest.getLessonName());
        Lesson savedLesson=lessonRepository.save(lessonMapper.mapLessonRequestToLesson(lessonRequest));
        return ResponseMessage.<LessonResponse>builder()
                .message(SuccessMessages.LESSON_SAVE)
                .object(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .status(HttpStatus.CREATED)
                .build();



    }

    public boolean isLessonExistByLessonName(String lessonName) {
      boolean lessonExist=  lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);

      if(lessonExist){
          throw  new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
      }else{
          return false;
      }
    }




}
