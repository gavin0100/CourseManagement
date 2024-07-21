package com.example.back_end_fams.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUnitRequest {
    private int unitCode;
    private String unitName;
    private String day;
    private String unit;
    private int numberOfHours;
}
