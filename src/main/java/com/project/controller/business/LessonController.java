package com.project.controller.business;

import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.LessonResponse;
import com.project.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor

public class LessonController {

    private final LessonService lessonService;


    @GetMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")

    public ResponseMessage<LessonResponse> saveLesson (@RequestBody @Valid LessonRequest lessonRequest){
        return lessonService.saveLesson(lessonRequest);

    }






}
