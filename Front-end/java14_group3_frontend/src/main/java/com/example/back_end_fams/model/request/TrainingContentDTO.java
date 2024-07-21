package com.example.back_end_fams.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TrainingContentDTO {
    private int Id;
    private String content;
    private String deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private int learningObjectiveId;

}
