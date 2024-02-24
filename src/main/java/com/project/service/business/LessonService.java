package com.project.service.business;

import com.project.entity.concretes.business.Lesson;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.LessonMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.LessonResponse;
import com.project.repository.business.LessonRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class LessonService {

    private final LessonRepository lessonRepository;;
    private final LessonMapper lessonMapper;
    private final PageableHelper  pageableHelper;

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


    public ResponseMessage deleteLessonById(Long id) {
        Lesson deletedLesson=lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, id)));
        lessonRepository.delete(deletedLesson);
        return ResponseMessage.<LessonResponse>builder()
              .message(SuccessMessages.LESSON_DELETE)
              .status(HttpStatus.OK)
              .build();
    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {
        boolean isExists=lessonRepository.getLessonByLessonName(lessonName).isPresent();
        Lesson lesson=  lessonRepository.getLessonByLessonName(lessonName).get();
        if(isExists) {
            return ResponseMessage.<LessonResponse>builder()
                    .message(SuccessMessages.LESSON_FOUND)
                    .object(lessonMapper.mapLessonToLessonResponse(lesson))
                    .build();
        }else{
            return ResponseMessage.<LessonResponse>builder()
                    .message(String.format(ErrorMessages.NOT_FOUND_LESSON_IN_LIST)).build();
        }
    }

    public Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return lessonRepository.findAll(pageable).map(lessonMapper::mapLessonToLessonResponse);
    }

    public Set<Lesson> getAllLessonByLessonId(Set<Long> idSet) {
        return idSet.stream()
                .map(this::isLessonExistById)
                .collect(Collectors.toSet());
    }
    Lesson isLessonExistById(Long id){
        return lessonRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE,id)));
    }

    public LessonResponse updateLesson(LessonRequest lessonRequest, Long lessonId) {
        Lesson lesson=isLessonExistById(lessonId);
        if(!(lesson.getLessonName().equals(lessonRequest.getLessonName()))&&(lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonRequest.getLessonName()))){
throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonId));
        }
Lesson updatedLesson=lessonMapper.mapLessonRequestToUpdatedLesson(lessonRequest,lessonId);
        updatedLesson.setLessonPrograms(lesson.getLessonPrograms());
       Lesson savedLesson=  lessonRepository.save(updatedLesson);
       return lessonMapper.mapLessonToLessonResponse(savedLesson);
    }



}
