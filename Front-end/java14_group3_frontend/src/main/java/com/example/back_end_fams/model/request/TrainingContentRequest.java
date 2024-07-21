package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.response.LearningObjectiveResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentRequest {
    private String content;
    private String deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private LearningObjectiveRequest learningObjective;

    private String unitName;
    private String day;
    private String unit;

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType.replace("/","_");
    }
}
