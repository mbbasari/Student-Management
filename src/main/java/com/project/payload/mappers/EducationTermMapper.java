package com.project.payload.mappers;

import com.project.entity.concretes.business.EducationTerm;
import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.response.business.EducationTermResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EducationTermMapper {

    public EducationTerm mapEducationTermRequestToEducationTerm(EducationTermRequest educationTermRequest){
        return EducationTerm.builder()
                .term(educationTermRequest.getTerm())
                .startDate(educationTermRequest.getStartDate())
                .endDate(educationTermRequest.getEndDate())
                .lastRegistrationDate(educationTermRequest.getLastRegistrationDate())
                .build();
    }

    public EducationTermResponse mapEducationTermToEducationTermResponse(EducationTerm savedEducationTerm) {
        return EducationTermResponse.builder()
              .id(savedEducationTerm.getId())
              .term(savedEducationTerm.getTerm())
              .startDate(savedEducationTerm.getStartDate())
              .endDate(savedEducationTerm.getEndDate())
              .lastRegistrationDate(savedEducationTerm.getLastRegistrationDate())
              .build();
    }

    public EducationTerm mapEducationTermRequestToUpdatedEducationTerm(Long id, EducationTermRequest educationTermRequest) {
        return mapEducationTermRequestToEducationTerm(educationTermRequest)
                .toBuilder()
                .id(id)
                .build();
    }
}
