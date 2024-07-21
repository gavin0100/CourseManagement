package com.example.back_end_fams.model.request;

import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingProgram;
import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class FileMaterialRequest {
    private int fileId;
    private String name;
    private String url;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;
    private User createdBy;
    private List<TrainingProgram> trainingPrograms;
    private List<TrainingContent> trainingContents;
}
