package com.project.contactMessage.repository;

import com.project.contactMessage.entity.ContactMessage;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {



    Page<ContactMessage> findByEmailEquals(String email, Pageable pageable);

    Page<ContactMessage>  findBySubjectEquals(String subject, Pageable pageable);

    @Query("SELECT c FROM ContactMessage c WHERE FUNCTION('DATE', c.dateTime) BETWEEN ?1 and ?2" )
    List<ContactMessage> findMessagesBetweenDates(LocalDate beginDate, LocalDate endDate);

}
