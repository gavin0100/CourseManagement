package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@Setter
//@Getter
//@Entity
//@Table(name = "training_program")
//@NoArgsConstructor
//@AllArgsConstructor
public class TrainingProgram implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "training_program_code")
//    private int trainingProgramCode;
//    @Column(name = "name", length = 50)
//    private String name;
//    @Column(name = "start_time")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//    @Column(name = "duration")
//    private int duration;
//    @Column(name = "topic_code")
//    private int topicCode;
//    @Column(name = "status", length = 50)
//    private String status;
//    @ManyToOne
//    @JoinColumn(name = "created_by_user_id")
//    private User createdBy;
//    @Column(name = "created_date")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createdDate;
//    @ManyToOne
//    @JoinColumn(name = "modified_by_user_id")
//    private User modifiedBy;
//    @Column(name = "modifedDate")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date modifiedDate;
//
//    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Class> classes;
//    @ManyToMany
//    @JsonIgnore
//    @JoinTable(
//            name = "training_program_file_material",
//            joinColumns = @JoinColumn(name = "training_program_code"),
//            inverseJoinColumns = @JoinColumn(name = "file_id")
//    )
//    private List<FileMaterial> trainingMaterials;
//
//    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<TrainingProgramSyllabus> trainingProgramSyllabuses;
}
