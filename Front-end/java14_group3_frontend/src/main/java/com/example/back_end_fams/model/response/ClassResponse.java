package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse implements Serializable {
    private int classId;
    private int trainingProgramCode;
    private String className;
    private String classCode;
    private int duration;
    private String status;
    private String location;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;

    private UserResponse createdBy;

    private UserResponse modifiedBy;

    List<SyllabusResponse> syllabusList;

    private TrainingProgramResponse2 trainingProgram;
}
