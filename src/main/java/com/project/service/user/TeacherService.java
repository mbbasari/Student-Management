package com.project.service.user;

import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.exception.ConflictException;
import com.project.payload.mappers.UserMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.user.TeacherRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
import com.project.payload.response.user.StudentResponse;
import com.project.payload.response.user.TeacherResponse;
import com.project.repository.UserRepository;
import com.project.service.UserRoleService;
import com.project.service.helper.MethodHelper;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TeacherService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final PasswordEncoder   passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final MethodHelper methodHelper;

    public ResponseMessage<TeacherResponse> saveTeacher(TeacherRequest teacherRequest) {

        uniquePropertyValidator.checkDuplicate(teacherRequest.getUsername(),teacherRequest.getSsn(),
                                          teacherRequest.getPhoneNumber(),teacherRequest.getEmail());

       User teacher= userMapper.mapTeacherRequestToUser(teacherRequest);
       teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
       teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

       if (teacherRequest.getIsAdvisorTeacher()){
           teacher.setIsAdvisor(Boolean.TRUE);
       }else teacher.setIsAdvisor(Boolean.FALSE);

        User savedTeacher=userRepository.save(teacher);
        return ResponseMessage.<TeacherResponse>builder()
                .message(SuccessMessages.TEACHER_SAVE)
                .object(userMapper.mapUserToTeacherResponse(savedTeacher))
                .status(HttpStatus.CREATED)
                .build();



    }

    public ResponseMessage<TeacherResponse> updateTeacherByManagers(TeacherRequest teacherRequest, Long userId) {

           User user= methodHelper.isUserExist(userId);
           methodHelper.checkRole(user,RoleType.TEACHER);
           uniquePropertyValidator.checkUniqueProperties(user,teacherRequest);
           User updatedTeacher= userMapper.mapTeacherRequestToUpdatedUser(teacherRequest, userId);
           updatedTeacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
           updatedTeacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
           User savedTeacher=userRepository.save(updatedTeacher);

           return ResponseMessage.<TeacherResponse>builder()
                   .message(SuccessMessages.TEACHER_UPDATE)
                   .object(userMapper.mapUserToTeacherResponse(savedTeacher))
                   .status(HttpStatus.OK)
                   .build();

    }


    public List<StudentResponse> getAllStudentByAdvisorUsername(String userName) {
        User teacher=methodHelper.isExistByUsername(userName);
        methodHelper.checkAdvisor(teacher);
        return userRepository.findByAdvisorTeacherId(teacher.getId())
                .stream()
                .map(userMapper::mapUserToStudentResponse)
                .collect(Collectors.toList());

    }

    public ResponseMessage<UserResponse> saveAdvisorTeacher(Long teacherId) {
        User teacher=methodHelper.isUserExist(teacherId);
        methodHelper.checkRole(teacher,RoleType.TEACHER);
        if(Boolean.TRUE.equals(teacher.getIsAdvisor())) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_EXIST_ADVISOR_MESSAGE,teacherId));
        }
        teacher.setIsAdvisor(Boolean.TRUE);
        userRepository.save(teacher);
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.ADVISOR_TEACHER_SAVE)
                .object(userMapper.mapUserToUserResponse(teacher))
                .status(HttpStatus.OK)
                .build();

    }


    public ResponseMessage<UserResponse> deleteAdvisorTeacherById(Long teacherId) {

      User teacher=  methodHelper.isUserExist(teacherId);
                     methodHelper.checkRole(teacher,RoleType.TEACHER);
                     methodHelper.checkAdvisor(teacher);
                     teacher.setIsAdvisor(Boolean.FALSE);
                     userRepository.save(teacher);

                     List <User>allStudents=userRepository.findByAdvisorTeacherId(teacherId);

                     if (!allStudents.isEmpty()){
                         allStudents.forEach(student->student.setIsAdvisor(null));
                     }


                     return ResponseMessage.<UserResponse>builder()
                                             .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
                                             .object(userMapper.mapUserToUserResponse(teacher))
                                             .status(HttpStatus.OK)
                                            .build();

    }



}
