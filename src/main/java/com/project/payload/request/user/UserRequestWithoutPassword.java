package com.project.payload.request.user;

import com.project.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor

public class UserRequestWithoutPassword extends AbstractUserRequest {
}
