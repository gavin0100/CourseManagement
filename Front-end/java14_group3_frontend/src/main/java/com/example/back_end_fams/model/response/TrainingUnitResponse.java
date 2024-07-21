package com.example.back_end_fams.model.response;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitResponse {
    private int unitCode;
    private String unitName;
    private int numberOfHours;
    private SyllabusResponse syllabus;
    private String day;
    private String unit;

    private List<TrainingContentResponse> trainingContents;
}
