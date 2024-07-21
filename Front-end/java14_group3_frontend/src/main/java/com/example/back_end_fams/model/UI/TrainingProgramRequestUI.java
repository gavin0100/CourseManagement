package com.example.back_end_fams.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramRequestUI {
    private int trainingProgramId;
    private String materialName;

    private int syllabusId;
}
