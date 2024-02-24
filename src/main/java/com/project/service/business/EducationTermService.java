package com.project.service.business;

import com.project.entity.concretes.business.EducationTerm;
import com.project.exception.BadRequestException;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mappers.EducationTermMapper;
import com.project.payload.messages.ErrorMessages;
import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.EducationTermResponse;
import com.project.repository.business.EducationTermRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {
    validateEducationTermDates(educationTermRequest);
   EducationTerm savedEducationTerm= educationTermRepository.save(educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest));

    return ResponseMessage.<EducationTermResponse>builder()
            .message(SuccessMessages.EDUCATION_TERM_SAVE)
            .status(HttpStatus.CREATED)
            .object(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
            .build();

    }


    private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest){

        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }

    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest) {

        validateEducationTermDatesForRequest(educationTermRequest);

        if (educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(),
                educationTermRequest.getStartDate().getYear())) {
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }

        if (educationTermRepository.finByYear(educationTermRequest.getStartDate().getYear())
                .stream()
                .anyMatch(educationTerm ->
                        (educationTerm.getStartDate().equals(educationTermRequest.getStartDate())
                                || (educationTerm.getStartDate().isBefore(educationTermRequest.getStartDate())
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getStartDate()))
                                || (educationTerm.getStartDate().isBefore(educationTermRequest.getEndDate())
                                && educationTerm.getEndDate().isAfter(educationTermRequest.getEndDate()))
                                || (educationTerm.getStartDate().isAfter(educationTermRequest.getStartDate())
                                && educationTerm.getEndDate().isBefore(educationTermRequest.getEndDate()))))) {
            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
        }


    }


    public EducationTermResponse getEducationTermById(Long id) {
    EducationTerm term=isEducationTermExist(id);
    return educationTermMapper.mapEducationTermToEducationTermResponse(term);

    }


    private EducationTerm isEducationTermExist(Long id){
        return educationTermRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id)));

    }


    public List<EducationTermResponse> getAllEducationTerms() {
        return educationTermRepository.findAll()
                .stream()
                .map(educationTermMapper::mapEducationTermToEducationTermResponse)
                .collect(Collectors.toList());
    }

    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {

        Pageable pageable= pageableHelper.getPageableWithProperties(page, size, sort, type);
        return educationTermRepository.findAll(pageable)
                .map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }


    public ResponseMessage<?> deleteEducationTermById(Long id) {
        EducationTerm term=isEducationTermExist(id);
        educationTermRepository.delete(term);
        return ResponseMessage.builder()
              .message(SuccessMessages.EDUCATION_TERM_DELETE)
              .status(HttpStatus.OK)
              .build();
    }


    public ResponseMessage<EducationTermResponse> updateEducationTerm(EducationTermRequest educationTermRequest, Long id) {
        isEducationTermExist(id);
        validateEducationTermDates(educationTermRequest);
        EducationTerm educationTermUpdated=
                educationTermRepository.save(educationTermMapper.mapEducationTermRequestToUpdatedEducationTerm(id,educationTermRequest));
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .status(HttpStatus.OK)
                .object(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermUpdated))
                .build();
    }

    public EducationTerm getAllEducationTermsById(Long id){
        return isEducationTermExist(id);
    }
}
