package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.response.TrainingProgramClassDTO;
import com.example.back_end_fams.model.response.TrainingProgramSyllabusDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class TrainingProgramCreateRequest {

    @NotBlank(message = "Please fill the name of new training program")
    @Size(max = 50)
    private String name;
    private Date startTime;
    private Integer estimatedDuration;
    private Integer topicCode;

    @Size(max = 50)
    private String status;
    private String generalInformation;
    private Date createdDate;
    private Date modifiedDate;

    private List<Integer> listTrainingProgramClass_ClassId;
    private List<Integer> listFileMaterial_FileId;
    private List<Integer> listTrainingProgramSyllabus_Syllabus_TopicCode;
}

