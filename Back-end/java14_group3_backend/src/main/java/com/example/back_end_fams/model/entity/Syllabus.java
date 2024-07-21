package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "syllabus")
@NoArgsConstructor
@AllArgsConstructor
public class Syllabus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_code")
    private int topicCode;
    @Column(name = "topic_name", length = 50)
    private String topicName;
    @Column(name = "technical_group", length = 500)
    private String technicalGroup;
    @Column(name = "version", length = 50)
    private String version;
    @Column(name = "training_audience")
    private int trainingAudience;
    @Column(name = "level")
    private String level;

    @Column(name="course_Objective")
    private String courseObjective;

    @Column(name = "duration")
    private int duration;

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TrainingUnit> topicOutline;
    @Column(name = "training_principles", length = 50)
    private String trainingPrinciples;
    @Column(name = "learning_objective", length = 500)
    private String learningObjective;
    @Column(name = "publish_status", length = 50)
    private String publishStatus;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "modified_by_user_id")
    private User modifiedBy;
    @Column(name = "modifedDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TrainingProgramSyllabus> trainingProgramSyllabuses;

    @ManyToMany
    @JoinTable(
            name = "syllabus_training_objective",
            joinColumns = @JoinColumn(name = "topic_code"),
            inverseJoinColumns = @JoinColumn(name = "code")
    )
    @JsonIgnore
    private Set<LearningObjective> learningObjectives = new HashSet<>();
    public void addLearningObjective(LearningObjective learningObjective) {
        this.learningObjectives.add(learningObjective);
        learningObjective.getSyllabuses().add(this);
    }

}
