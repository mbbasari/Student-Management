package com.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {

    @NotNull(message = "Username can not be empty")
    private String username;

    @NotNull(message = "Password can not be empty")
    private String password;
}
