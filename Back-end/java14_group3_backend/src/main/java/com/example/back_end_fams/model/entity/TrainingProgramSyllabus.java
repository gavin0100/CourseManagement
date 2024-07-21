package com.example.back_end_fams.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "training_program_syllabus")
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgramSyllabus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "topic_code")
    private Syllabus syllabus;
    @ManyToOne
    @JoinColumn(name = "training_program_code")
    private TrainingProgram trainingProgram;
    @JoinColumn(name="sequence")
    private int sequence;
}
