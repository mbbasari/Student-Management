package com.project.controller.business;

import com.project.entity.concretes.business.Lesson;
import com.project.payload.request.business.LessonRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.LessonResponse;
import com.project.service.business.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage deleteLessonById(@PathVariable Long id) {
        return lessonService.deleteLessonById(id);
    }

@GetMapping("/getLessonByName")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
   public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName) {
        return lessonService.getLessonByLessonName(lessonName);
}

@GetMapping("/findLessonByPage")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")

public Page<LessonResponse> findLessonByPage(
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "size",defaultValue = "10") int size,
        @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
        @RequestParam(value = "type",defaultValue = "desc") String type){
    return lessonService.findLessonByPage(page,size,sort,type);
}

@GetMapping("/getAllLessonByLessonId")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")

public Set<Lesson> getAllLessonByLessonId(@RequestParam(name ="lessonId") Set<Long> idSet){
        return lessonService.getAllLessonByLessonId(idSet);
}

@PutMapping("/update/{lessonId}")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<LessonResponse> updateLessonById(@RequestBody @Valid LessonRequest lessonRequest, @PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonRequest,lessonId));
    }



}
