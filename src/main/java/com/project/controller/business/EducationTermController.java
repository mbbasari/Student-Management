package com.project.controller.business;

import com.project.payload.request.business.EducationTermRequest;
import com.project.payload.response.ResponseMessage;
import com.project.payload.response.business.EducationTermResponse;
import com.project.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/educationTerms")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(
            @RequestBody @Valid EducationTermRequest educationTermRequest) {

        return educationTermService.saveEducationTerm(educationTermRequest);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public EducationTermResponse getEducationTermById(@PathVariable Long id) {
       return educationTermService.getEducationTermById(id);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
       public List<EducationTermResponse> getAllEducationTerms(){
        return educationTermService.getAllEducationTerms();
    }

    @GetMapping("/getAllEducationTermsByPage")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER')")
    public Page<EducationTermResponse>getAllEducationTermsByPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "startDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type){
        return educationTermService.getAllEducationTermsByPage(page,size,sort,type);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<?> deleteEducationTermById(@PathVariable Long id) {
        return educationTermService.deleteEducationTermById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<EducationTermResponse> updateEducationTerm(
            @RequestBody @Valid EducationTermRequest educationTermRequest, @PathVariable Long id) {
            return educationTermService.updateEducationTerm(educationTermRequest,id);
    }


}
