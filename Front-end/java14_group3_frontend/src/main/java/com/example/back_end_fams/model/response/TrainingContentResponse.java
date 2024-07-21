package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentResponse {
    private int Id;
    private String content;
    private String deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private LearningObjectiveResponse learningObjective;
    private TrainingUnitResponse trainingUnit;

    private List<FileMaterialResponse> trainingMaterials;
}
