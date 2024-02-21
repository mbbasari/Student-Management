package com.project.payload.request.user;

import com.project.payload.request.abstracts.BaseUserRequest;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.validation.constraints.NotNull;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class TeacherRequest extends BaseUserRequest {


    @NotNull(message = "please select lesson")
    private Set<Long> lessonIdList;

    @NotNull(message = "please select isAdvisor teacher")
    private Boolean isAdvisorTeacher;




}
