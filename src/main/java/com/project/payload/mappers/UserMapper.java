package com.project.payload.mappers;

import com.project.entity.concretes.user.User;
import com.project.payload.request.abstracts.BaseUserRequest;
import com.project.payload.request.user.StudentRequest;
import com.project.payload.request.user.TeacherRequest;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.UserResponse;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.payload.response.user.StudentResponse;
import com.project.payload.response.user.TeacherResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthDay())
                .ssn(user.getSsn())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRoleType().name())
                .build();

    }


    public User mapUserRequestToUser(BaseUserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(userRequest.getPassword())
                .ssn(userRequest.getSsn())
                .birthDay(userRequest.getBirthDay())
                .birthPlace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .built_in(userRequest.getBuildIn())
                //since we will not save teacher with these ent-point

                .build();


    }

    public StudentResponse mapUserToStudentResponse(User student) {

        return StudentResponse.builder()
                .userId(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .birthDay(student.getBirthDay())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .studentNumber(student.getStudentNumber())
                .isActive(student.isActive())
                .build();


    }

    public TeacherResponse mapUserToTeacherResponse(User teacher) {

        return TeacherResponse.builder()
                .userId(teacher.getId())
                .username(teacher.getUsername())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .birthDay(teacher.getBirthDay())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .lessonPrograms(teacher.getLessonProgramList())
                .isAdvisorTeacher(teacher.getIsAdvisor())
                .build();
        
    }
    public User mapUserRequestToUpdateUser(UserRequest userRequest,Long userId){
        return User.builder()
              .id(userId)
              .username(userRequest.getUsername())
              .name(userRequest.getName())
              .surname(userRequest.getSurname())
              .password(userRequest.getPassword())
              .ssn(userRequest.getSsn())
              .birthDay(userRequest.getBirthDay())
              .birthPlace(userRequest.getBirthPlace())
              .phoneNumber(userRequest.getPhoneNumber())
              .gender(userRequest.getGender())
              .email(userRequest.getEmail())

              .build();
    }
    public User mapTeacherRequestToUser(TeacherRequest teacherRequest){
        return User.builder()
               .username(teacherRequest.getUsername())
               .name(teacherRequest.getName())
               .surname(teacherRequest.getSurname())
               .password(teacherRequest.getPassword())
               .ssn(teacherRequest.getSsn())
               .birthDay(teacherRequest.getBirthDay())
               .birthPlace(teacherRequest.getBirthPlace())
               .phoneNumber(teacherRequest.getPhoneNumber())
               .gender(teacherRequest.getGender())
               .email(teacherRequest.getEmail())
               .built_in(teacherRequest.getBuildIn())
               .isAdvisor(teacherRequest.getIsAdvisorTeacher())

               .build();
    }


    public User mapTeacherRequestToUpdatedUser(TeacherRequest request, Long userId){
        return User.builder()
              .id(userId)
              .username(request.getUsername())
              .name(request.getName())
              .surname(request.getSurname())
              .password(request.getPassword())
              .ssn(request.getSsn())
              .birthDay(request.getBirthDay())
              .birthPlace(request.getBirthPlace())
              .phoneNumber(request.getPhoneNumber())
              .gender(request.getGender())
              .email(request.getEmail())
              .isAdvisor(request.getIsAdvisorTeacher())
              .build();

    }

    public User mapStudentRequestToUser(StudentRequest studentRequest){
        return User.builder()
                .fatherName(studentRequest.getFatherName())
                .motherName(studentRequest.getMotherName())
                .name(studentRequest.getName())
                .surname(studentRequest.getSurname())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .birthDay(studentRequest.getBirthDay())
                .birthPlace(studentRequest.getBirthPlace())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .email(studentRequest.getEmail())
                .built_in(studentRequest.getBuildIn())
                .build();

    }


    public User mapStudentRequestToUpdateUser(StudentRequest studentRequest, Long userId) {

        User student=mapStudentRequestToUser(studentRequest);
        student.setId(userId);
        return student;

    }
}
