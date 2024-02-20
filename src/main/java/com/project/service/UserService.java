package com.project.service;

import com.project.entity.concretes.user.User;
import com.project.entity.enums.RoleType;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.UserMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.user.UserRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.repository.UserRepository;
import com.project.service.helper.MethodHelper;
import com.project.service.helper.PageableHelper;
import com.project.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;


    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        uniquePropertyValidator.checkDuplicate(userRequest.getUsername(),userRequest.getSsn(),
                                               userRequest.getPhoneNumber(),userRequest.getEmail());


        User user = userMapper.mapUserRequestToUser(userRequest);


if(userRole.equalsIgnoreCase(RoleType.ADMIN.name)){

    if (Objects.equals(userRequest.getUsername(),"SuperAdmin")){
        user.setBuilt_in(true);
    }
    user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
    }else if(userRole.equalsIgnoreCase(RoleType.MANAGER.name)){
    user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
    } else if (userRole.equalsIgnoreCase("ViceDean")) {
    user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
    }else {
    throw new ResourceNotFoundException(
            String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE, userRole));
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setIsAdvisor(Boolean.FALSE);
    User savedUser = userRepository.save(user);
    return ResponseMessage.<UserResponse>builder()
            .message(SuccessMessages.USER_CREATE)
            .object(userMapper.mapUserToUserResponse(savedUser))
            .build();


    }

    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return userRepository.findByUserByRole(userRole, pageable).map(userMapper::mapUserToUserResponse);
    }

    public ResponseMessage<BaseUserResponse> getUserById(Long userId) {
        BaseUserResponse baseUserResponse=null;
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId)));
        if(user.getUserRole().getRoleType() == RoleType.STUDENT){
            baseUserResponse=userMapper.mapUserToStudentResponse(user);
        } else if (user.getUserRole().getRoleType() == RoleType.TEACHER) {
            baseUserResponse=userMapper.mapUserToTeacherResponse(user);

        }else {
            baseUserResponse=userMapper.mapUserToUserResponse(user);
        }
        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .object(baseUserResponse)
                .status(HttpStatus.OK)
                .build();

    }


    public String deleteUserById(Long id, HttpServletRequest request) {
        User user= methodHelper.isUserExist(id);
        String userName= (String) request.getAttribute("username");
        User user2=userRepository.findByUsername(userName);// silinme talebinde bulunan

        if (Boolean.TRUE.equals(user.getBuilt_in())){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }else if(user2.getUserRole().getRoleType()==RoleType.MANAGER){

            if (!(  user2.getUserRole().getRoleType() == RoleType.TEACHER ||
                    user2.getUserRole().getRoleType()  == RoleType.STUDENT ||
                    user2.getUserRole().getRoleType()  == RoleType.ASSISTANT_MANAGER)) {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
            }

        }else if (user2.getUserRole().getRoleType() == RoleType.ASSISTANT_MANAGER) {
            if (!(user2.getUserRole().getRoleType() == RoleType.TEACHER ||
                    user2.getUserRole().getRoleType()  == RoleType.STUDENT)) {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
            }

    }
        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;
}


    public ResponseMessage<BaseUserResponse> updateAdminDeanViceDeanByAdmin(Long userId, UserRequest userRequest) {
        User user= methodHelper.isUserExist(userId);
        methodHelper.checkBuiltIn(user);
        uniquePropertyValidator.checkDuplicate();
    }
}
