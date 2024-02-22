package com.project.controller.user;

import com.project.payload.request.user.TeacherRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
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
@RequestMapping("/TEACHER")

@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity <ResponseMessage<TeacherResponse>> saveTeacher(
            @RequestBody @Valid TeacherRequest teacherRequest) {

        return ResponseEntity.ok(teacherService.saveTeacher(teacherRequest));

    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT')")
    public ResponseMessage<TeacherResponse> updateTeacherByManagers(
            @RequestBody @Valid TeacherRequest teacherRequest, @PathVariable Long userId) {

        return teacherService.updateTeacherByManagers(teacherRequest, userId);
    }

@GetMapping("/gelAllStudentByAdvisorUsername")
@PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<StudentResponse> getAllStudentByAdvisorUsername(HttpServletRequest request) {
    String userName = request.getHeader("username");
    return teacherService.getAllStudentByAdvisorUsername(userName);

    }

    @PatchMapping("/saveAdvisorTeacher/{teacherId")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<UserResponse> saveAdvisorTeacher(@PathVariable Long teacherId) {

        return teacherService.saveAdvisorTeacher(teacherId);
    }

    @DeleteMapping("/deleteAdvisorTeacherById/{teacherId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<UserResponse> deleteAdvisorTeacherById(@PathVariable Long teacherId) {

        return teacherService.deleteAdvisorTeacherById(teacherId);
    }

@GetMapping("/getAllAdvisorTeacher")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
public List<UserResponse> getAllAdvisorTeacher() {

        return teacherService.getAllAdvisorTeacher();
    }


}
