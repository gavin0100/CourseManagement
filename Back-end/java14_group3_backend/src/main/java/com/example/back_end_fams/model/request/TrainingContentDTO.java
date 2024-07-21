package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.TrainingContent;
import lombok.Data;

@Data
public class TrainingContentDTO {
    private int Id;
    private String content;
    private TrainingContent.DeliveryTypeEnum deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private int learningObjectiveId;
    public boolean getTrainingFormat() {
        return this.trainingFormat;
    }

}
