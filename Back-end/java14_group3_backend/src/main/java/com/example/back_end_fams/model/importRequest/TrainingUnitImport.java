package com.example.back_end_fams.model.importRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitImport {
    private int unitCode;
    private String unitName;
    private String unit;
    private String day;
    private int numberOfHours;
    private int topic_code;
}
