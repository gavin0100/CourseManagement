package com.example.back_end_fams.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultiStep_Syllabus_Unit_Content {
    private SyllabusRequest syllabusRequest;

    private List<TrainingUnitRequest> trainingUnitRequests;
    private List<TrainingContentRequest> trainingContentRequests;
}
