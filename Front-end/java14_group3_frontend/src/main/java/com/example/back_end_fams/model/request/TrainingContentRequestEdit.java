package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.response.LearningObjectiveResponse;
import com.example.back_end_fams.model.response.TrainingUnitResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentRequestEdit {
    private int id;
    private String content;
    private String deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private LearningObjectiveRequest learningObjective;
}
