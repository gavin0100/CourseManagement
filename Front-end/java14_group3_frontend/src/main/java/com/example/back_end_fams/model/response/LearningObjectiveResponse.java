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
public class LearningObjectiveResponse {
    private int code;
    private String name;
    private String type;
    private String description;

    private List<SyllabusResponse> syllabuses;

    private List<TrainingContentResponse> trainingContents;
}
