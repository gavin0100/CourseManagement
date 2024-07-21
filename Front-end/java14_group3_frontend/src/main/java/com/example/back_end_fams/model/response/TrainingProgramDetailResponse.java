package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainingProgramDetailResponse {
    private int trainingProgramCode;
    private String name;
    private Date createdDate;
    private int duration;
    private String status;
    private String modifiedBy;
    private Date modifiedDate;
    private String generalInformation;
    private List<TrainingProgramSyllabusDTO> trainingProgramSyllabusDTOS;
    private List<TrainingProgramClassDTO> trainingProgramClassDTOS;
}
