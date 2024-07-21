package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "learning_objective")
@NoArgsConstructor
@AllArgsConstructor
public class LearningObjective implements Serializable {
    @Id
    @Column(name = "file_id")
    private int code;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "type", length = 50)
    private String type;
    @Column(name = "description", length = 500)
    private String description;

    @ManyToMany(mappedBy = "learningObjectives")
    @JsonIgnore
    private Set<Syllabus> syllabuses = new HashSet<>();

    @OneToMany(mappedBy = "learningObjective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TrainingContent> trainingContents;
}
