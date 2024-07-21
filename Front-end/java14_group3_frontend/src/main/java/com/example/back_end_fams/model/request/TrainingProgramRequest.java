package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramRequest implements Serializable {

    @NotBlank(message = "Please fill the name of new training program")
    @Size(max = 50)
    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    private Integer topicCode;
    private Integer estimatedDuration;

    @Size(max = 50)
    private String status;
    private String generalInformation;
    private User createdBy;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private User modifiedBy;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    private List<TrainingProgramSyllabus> trainingProgramSyllabuses;
}