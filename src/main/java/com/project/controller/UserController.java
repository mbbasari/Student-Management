package com.project.controller;

import com.project.payload.request.user.UserRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.UserResponse;
import com.project.payload.response.abstracts.BaseUserResponse;
import com.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/save/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(
            @PathVariable String userRole,
            @RequestBody @Valid UserRequest request) {

        return ResponseEntity.ok(userService.saveUser(request, userRole));
    }

    @GetMapping("/getAllUserByPage/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUserByPage(
            @PathVariable String userRole,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        Page<UserResponse> adminOrDeans = userService.getUsersByPage(page, size, sort, type, userRole);
        ResponseEntity<Page<UserResponse>> response = new ResponseEntity<>(adminOrDeans, HttpStatus.OK);
        return response;

    }

    @GetMapping("/getUserById/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT','ASSISTANT_MANAGER')")

    public ResponseEntity<String> deleteUserById(@PathVariable Long id, HttpServletRequest httpRequest) {
    return ResponseEntity.ok(userService.deleteUserById(id, httpRequest));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BaseUserResponse>updateAdminDeanViceDeanByAdmin(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable Long userId){
        return userService.updateAdminDeanViceDeanByAdmin(userId,userRequest);
    }

}