package com.project.payload.mappers;

import com.project.entity.concretes.user.User;
import com.project.payload.response.UserResponse;
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

}
