package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.response.SyllabusResponse;
import com.example.back_end_fams.model.response.TrainingContentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUnitRequest {
    private String unitName;
    private int numberOfHours;
    private String day;
    private String unit;
}
