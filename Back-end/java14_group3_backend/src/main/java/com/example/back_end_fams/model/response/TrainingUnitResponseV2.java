package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUnitResponseV2 implements Serializable {
    private int unitCode;
    private String unitName;
    private String day;
    private String unit;
    private int numberOfHours;
    private List<TrainingContentResponse> trainingContents;
}
