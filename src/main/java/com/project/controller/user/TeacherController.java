package com.project.controller.user;

import com.project.payload.request.user.TeacherRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.payload.response.user.TeacherResponse;
import com.project.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")

@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity <ResponseMessage<TeacherResponse>> saveTeacher(
            @RequestBody @Valid TeacherRequest teacherRequest) {

        return ResponseEntity.ok(teacherService.saveTeacher(teacherRequest));

    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    public ResponseMessage<TeacherResponse> updateTeacherByManagers(
            @RequestBody @Valid TeacherRequest teacherRequest, @PathVariable Long userId) {

        return teacherService.updateTeacherByManagers(teacherRequest, userId);
    }

@GetMapping("/gelAllStudentByAdvisorUsername")
@PreAuthorize("hasAnyAuthority('Teacher')")
    public List<StudentResponse> getAllStudentByAdvisorTeacher(HttpServletRequest request) {
String userName = request.getHeader("username");
        return teacherService.getAllStudentByAdvisorTeacher(userName);
    }

}
