package com.example.back_end_fams.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "training_content_file_material_temp")
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentFileMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "training_content_id")
    private TrainingContent trainingContent;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileMaterial fileMaterial;

}
