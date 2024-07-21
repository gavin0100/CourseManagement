package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.model.entity.TrainingUnit;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingContentRequest {
    private int Id;
    private String content;
    private String deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
}
