package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.response.TrainingProgramSyllabusDTO;
import lombok.Data;

import java.util.List;

@Data
public class TrainingProgramUpdateRequest {
    private String name;
    private String generalInformation;
    private Integer estimatedDuration;
    private List<Integer> listTrainingProgramSyllabus_Syllabus_TopicCode;
}
