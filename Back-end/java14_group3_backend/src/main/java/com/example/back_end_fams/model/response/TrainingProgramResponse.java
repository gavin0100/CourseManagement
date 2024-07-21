package com.example.back_end_fams.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TrainingProgramResponse {

    private int trainingProgramCode;
    private String name;
    private Date createdDate;
    private int duration;
    private String status;
    private String createdBy;

    private String generalInformation;

    private Date modifiedDate;
}
