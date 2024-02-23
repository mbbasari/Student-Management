package com.project.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonResponse {

    private Long lessonId;
    private String lessonName;
    private Integer creditScore;
    private boolean isCompulsory;
}
