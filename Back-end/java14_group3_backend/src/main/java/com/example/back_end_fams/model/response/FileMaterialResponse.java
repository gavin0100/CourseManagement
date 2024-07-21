package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingProgram;
import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileMaterialResponse implements Serializable {
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
