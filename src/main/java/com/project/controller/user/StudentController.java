package com.project.controller.user;

import com.project.payload.request.user.StudentRequest;
<<<<<<< HEAD
import com.project.payload.request.user.StudentRequestWithoutPassword;
=======
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a
import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(
            @RequestBody @Valid StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.saveStudent(studentRequest));
    }

<<<<<<< HEAD
    @PatchMapping("/update")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid
                                        StudentRequestWithoutPassword studentRequestWithoutPassword,
                                    HttpServletRequest request){

        return studentService.updateStudent(studentRequestWithoutPassword, request);

    }
@PutMapping("/update/{studentId")
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<StudentResponse>updateStudentByManagers(
            @RequestBody @Valid StudentRequest studentRequest,
            @PathVariable Long userId){
        return studentService.updateStudentByManagers(studentRequest, userId);
            }

    @GetMapping("/changeStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage changeStatusOfStudent(@RequestParam Long id, @RequestParam boolean status){

        return studentService.changeStatusOfStudent(id, status);
    }
=======
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a



}
