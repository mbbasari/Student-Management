package com.project.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.entity.enums.Term;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class EducationTerm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Education term can not be null")
    @Enumerated(EnumType.STRING)
    private Term term;

    @NotNull(message = "Start date can not be null")
    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd" )
    private LocalDate startDate;

    @NotNull(message = "End date can not be null")
    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd" )
    private LocalDate endDate;

    @NotNull(message = "Last registration date can not be null")
    @Column(name = "last_registration_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd" )
    private LocalDate lastRegistrationDate;


    @OneToMany(mappedBy = "educationTerm", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LessonProgram> lessonPrograms;



}
