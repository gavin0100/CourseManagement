package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.entity.User;
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
public class SyllabusRequest {
    private int topicCode;
    private User createdBy;
    private User modifiedBy;
    private String level;
    private int trainingAudience;
    private List<TrainingUnit> topicOutline;
    private List<LearningObjective> learningObjectives;
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
}
