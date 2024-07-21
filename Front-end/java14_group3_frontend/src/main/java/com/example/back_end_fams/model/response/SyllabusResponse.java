package com.example.back_end_fams.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusResponse {
    private int topicCode;
    private UserResponse createdBy;
    private UserResponse modifiedBy;
    private String level;
    private int trainingAudience;
    //them
    private String courseObjective;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;

    private String publishStatus;
    private String topicName;
    private String trainingPrinciples;
    private String version;
    private String technicalGroup;
    private List<TrainingUnitResponse> topicOutline;
    private int duration;
    private List<TrainingProgramSyllabusResponse> trainingProgramSyllabuses;

    private List<LearningObjectiveResponse> learningObjectives;

}
