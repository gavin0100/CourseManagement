package com.example.back_end_fams.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTrainingContentRequestIUI {
    private int contentId;
    private String contentName;
    private String contentLearningObjectiveName;
    private int contentDuration;
    private String contentStatus;
    private String contentDelivery;
}
