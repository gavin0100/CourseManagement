package com.example.back_end_fams.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusRequest {
    private String topicName;
    private String technicalGroup;
    private String version;
    private int trainingAudience;
    private String trainingPrinciples;
    private String publishStatus;
    private int duration;
    private String level;
    private String courseObjective;
}
