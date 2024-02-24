package com.project.service.user;

import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.payload.mappers.UserMapper;
<<<<<<< HEAD
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.user.StudentRequest;
import com.project.payload.request.user.StudentRequestWithoutPassword;
=======
import com.project.payload.request.user.StudentRequest;
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.user.StudentResponse;
import com.project.repository.UserRepository;
import com.project.service.UserRoleService;
import com.project.service.helper.MethodHelper;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
=======
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a

@Service
@RequiredArgsConstructor


public class StudentService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final MethodHelper  methodHelper;
    private final UserMapper userMapper;
    private  final PasswordEncoder  passwordEncoder;
    private final UserRoleService userRoleService;

    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {

       User advisorTeacher=methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
       methodHelper.checkAdvisor(advisorTeacher);
       uniquePropertyValidator.checkDuplicate(studentRequest.getUsername(),
                                              studentRequest.getSsn(),
                                              studentRequest.getPhoneNumber(),
                                              studentRequest.getEmail());

       User student=userMapper.mapStudentRequestToUser(studentRequest);

       student.setAdvisorTeacherId(advisorTeacher.getId());
       student.setPassword(passwordEncoder.encode(student.getPassword()));
       student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
       student.setActive(true);
       student.setIsAdvisor(Boolean.FALSE);
<<<<<<< HEAD
       student.setStudentNumber(getLastNumber());
       return ResponseMessage.<StudentResponse>builder()
               .object(userMapper.mapUserToStudentResponse(userRepository.save(student)))
               .message(SuccessMessages.STUDENT_SAVE)
               .build();

    }














    private  int getLastNumber(){
        if(!userRepository.findStudentForNumber(RoleType.STUDENT)){
            return 1000;
        }
      return userRepository.getMaxStudentNumber()+1;

    }

    public ResponseEntity<String> updateStudent(StudentRequestWithoutPassword studentRequest, HttpServletRequest request) {
    String username= (String) request.getAttribute("username");
    User student=userRepository.findByUsername(username);

    uniquePropertyValidator.checkUniqueProperties(student, studentRequest);

        student.setMotherName(studentRequest.getMotherName());
        student.setFatherName(studentRequest.getFatherName());
        student.setBirthPlace(studentRequest.getBirthPlace());
        student.setBirthDay(studentRequest.getBirthDay());
        student.setEmail(studentRequest.getEmail());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setGender(studentRequest.getGender());
        student.setName(studentRequest.getName());
        student.setSurname(studentRequest.getSurname());
        student.setSsn(studentRequest.getSsn());

        userRepository.save(student);

        String message =SuccessMessages.STUDENT_UPDATE;
        return ResponseEntity.ok(message);

    }

    public ResponseMessage<StudentResponse> updateStudentByManagers(StudentRequest studentRequest, Long userId) {
        User user=methodHelper.isUserExist(userId);
        methodHelper.checkRole(user, RoleType.STUDENT);
        uniquePropertyValidator.checkUniqueProperties(user,studentRequest);
//
//     alternatif setleme
//        User studentForUpdate=userMapper.mapStudentRequestToUpdateUser(studentRequest, userId);
//        studentForUpdate.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
//        studentForUpdate.setAdvisorTeacherId(studentRequest.getAdvisorTeacherId());
//        studentForUpdate.setStudentNumber(user.getStudentNumber());
//        studentForUpdate.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
//        studentForUpdate.setActive(true);


        user.setMotherName(studentRequest.getMotherName());
        user.setFatherName(studentRequest.getFatherName());
        user.setBirthPlace(studentRequest.getBirthPlace());
        user.setBirthDay(studentRequest.getBirthDay());
        user.setEmail(studentRequest.getEmail());
        user.setPhoneNumber(studentRequest.getPhoneNumber());
        user.setGender(studentRequest.getGender());
        user.setName(studentRequest.getName());
        user.setSurname(studentRequest.getSurname());
        user.setSsn(studentRequest.getSsn());
        user.setAdvisorTeacherId(studentRequest.getAdvisorTeacherId());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));



        return ResponseMessage.<StudentResponse>builder()
                .object(userMapper.mapUserToStudentResponse(userRepository.save(user)))
                .message(SuccessMessages.STUDENT_UPDATE)
                .status(HttpStatus.CREATED)
                .build();

    }


    public ResponseMessage changeStatusOfStudent(Long id, boolean status) {
        User student=methodHelper.isUserExist(id);
        methodHelper.checkRole(student, RoleType.STUDENT);
        student.setActive(status);
        userRepository.save(student);

       return ResponseMessage.builder()
               .message("Student is "+(status ? "active" : "passive"))
               .status(HttpStatus.OK)
               .build();
    }









=======
       student.setStudentNumber();





    }

    private  int getLastNumber(){

    }
>>>>>>> f43b28c002b955034e8e17b36c343582b289e23a
}
