package com.example.back_end_fams.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgramSyllabusResponse {
    private int id;
    private SyllabusResponse syllabus;
    private TrainingProgramResponse trainingProgram;
    private int sequence;
}
