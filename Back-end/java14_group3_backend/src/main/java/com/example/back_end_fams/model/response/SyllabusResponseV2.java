package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusResponseV2 implements Serializable {
    private int topicCode;
    private String topicName;
    private String technicalGroup;
    private String version;
    private int trainingAudience;
    private String level;
    private String courseObjective;
    private int duration;
    private String trainingPrinciples;
    private String publishStatus;

    private UserResponse createdBy;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    private UserResponse modifiedBy;

    //private List<TrainingUnit> topicOutline;
    private Set<LearningObjective> learningObjectives;

    private List<TrainingUnitResponseV2> topicOutline;
}
