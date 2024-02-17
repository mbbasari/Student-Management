package com.project.contactMessage.service;

import com.project.contactMessage.dto.ContactMessageRequest;
import com.project.contactMessage.dto.ContactMessageResponse;
import com.project.contactMessage.entity.ContactMessage;
import com.project.contactMessage.mapper.ContactMessageMapper;
import com.project.contactMessage.messages.Messages;
import com.project.contactMessage.repository.ContactMessageRepository;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

private final ContactMessageRepository contactMessageRepository;
private final ContactMessageMapper contactMessageMapper;

public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest request) {
   ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(request);
   ContactMessage savedData  = contactMessageRepository.save(contactMessage);
   ContactMessageResponse contactMessageResponse= contactMessageMapper.contactMessageToResponse(savedData);

   return ResponseMessage.<ContactMessageResponse>builder()
           .message("Contactmessage created successfully")
           .object(contactMessageResponse)
           .status(HttpStatus.CREATED)
           .build();
    }


    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
    if(Objects.equals(type,"desc")){
      pageable = PageRequest.of(page, size, Sort.by(sort).descending());
    }
    return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

       //return contactMessageRepository.findByEmailEquals(email, pageable).map(contactMessageMapper::contactMessageToResponse); boylede yazilabilirdi
        return contactMessageRepository.findByEmailEquals(email, pageable).map(e->contactMessageMapper.contactMessageToResponse(e));

    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
    if(Objects.equals(type,"desc")){
      pageable = PageRequest.of(page, size, Sort.by(sort).descending());
    }
    return contactMessageRepository.findBySubjectEquals(subject, pageable).map(contactMessageMapper::contactMessageToResponse);
    }


    public String deleteById(Long contactMessageId) {
    getContactMessageById(contactMessageId);
    contactMessageRepository.deleteById(contactMessageId);
    return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }


    public ContactMessage getContactMessageById(Long Id) {
    return contactMessageRepository.findById(Id).orElseThrow(
            ()-> new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }


    public List<ContactMessage> searchByDateBetween(String beginDateString, String endDateString) {
        try {
            LocalDate beginDate   = LocalDate.parse(beginDateString);
            LocalDate endDate   = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_FORMAT);
        }
    }


    public List<ContactMessage> searchBetweenTimes(String startHour, String startMinute, String endHour, String endMinute) {
        try {
            int startH=Integer.parseInt(startHour);
            int startM=Integer.parseInt(startMinute);
            int endH=Integer.parseInt(endHour);
            int endM=Integer.parseInt(endMinute);

            return contactMessageRepository.findMessagesBetweenTimes(startH, startM, endH, endM);
        } catch (NumberFormatException e) {
            throw new ConflictException(Messages.WRONG_TIME_FORMAT);
        }
    }
}
