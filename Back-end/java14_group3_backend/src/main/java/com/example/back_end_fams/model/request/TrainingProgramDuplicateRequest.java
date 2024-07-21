package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.model.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TrainingProgramDuplicateRequest {
    private String name;
    private Date startTime;
    private int duration;
    private int topicCode;
    private String status;
    private String generalInformation;
    private User createdBy;
    private Date createdDate;
    private User modifiedBy;
    private Date modifiedDate;
    private List<Class> classes;
    private List<FileMaterial> trainingMaterials;
    private List<TrainingProgramSyllabus> trainingProgramSyllabuses;
}
