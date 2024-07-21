package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgramSyllabusResponse implements Serializable {
    private int id;
    private int sequence;
    private int trainingProgramCode;
    private int syllabusCode;
    private TrainingProgramResponse2 trainingProgram;
    private SyllabusResponse syllabus;
}
