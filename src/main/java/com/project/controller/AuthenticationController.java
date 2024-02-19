package com.project.controller;

import com.project.payload.request.LoginRequest;

import com.project.payload.response.AuthResponse;
import com.project.service.AuthenticationService;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

private  final UserService  userService;
private final AuthenticationService authenticationService;


@PostMapping("/login") //http://localhost:8080/auth/login
public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

return authenticationService.authenticateUser(loginRequest);



}

}
