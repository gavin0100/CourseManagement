package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.response.SyllabusResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitRequestEdit {
    private int unitCode;
    private String unitName;
    private int numberOfHours;
    private String day;
    private String unit;
}
