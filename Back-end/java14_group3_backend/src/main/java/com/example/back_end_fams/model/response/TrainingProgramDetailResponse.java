package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainingProgramDetailResponse {
    private Integer trainingProgramCode;
    private String name;
    private Date createdDate;
    private Integer duration;
    private String status;
    private String modifiedBy;
    private Date modifiedDate;
    private String generalInformation;
    private List<TrainingProgramSyllabusDTO> trainingProgramSyllabusDTOS;
    private List<TrainingProgramClassDTO> trainingProgramClassDTOS;
    private List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOS;
}
