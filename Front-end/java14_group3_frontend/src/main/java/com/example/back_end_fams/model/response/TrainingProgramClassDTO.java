package com.example.back_end_fams.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class TrainingProgramClassDTO {
    private String classId;
    private String className;
    private String classCode;
    private Date createdDate;
    private Integer duration;
    private String status;
    private String location;
}
