package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "class")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Class implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;
    @Column(name = "training_program_code")
    private int trainingProgramCode;
    @Column(name = "class_name", length = 50)
    private String className;
    @Column(name = "class_code", length = 50)
    private String classCode;
    @Column(name = "duration")
    private int duration;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "location")
    private String location;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    private List<User> users;
    @ManyToOne
    @JoinColumn(name = "modified_by_user_id")
    private User modifiedBy;
    @Column(name = "modified_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    @ManyToOne
    @JoinColumn(name = "training_program_id")
    private TrainingProgram trainingProgram;

    @JsonIgnore
    @OneToMany(mappedBy = "class_room", cascade = CascadeType.ALL)
    private List<ClassDate> classDates;
}
