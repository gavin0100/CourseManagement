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
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "file_material")
@NoArgsConstructor
@AllArgsConstructor
public class FileMaterial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "url", length = 200)
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToMany(mappedBy = "trainingMaterials")
    @JsonIgnore
    private List<TrainingProgram> trainingPrograms;
    @ManyToMany(mappedBy = "trainingMaterials")
    @JsonIgnore
    private List<TrainingContent> trainingContents;

    @OneToMany(mappedBy = "fileMaterial")
    @JsonIgnore
    private List<TrainingContentFileMaterial> trainingContentFileMaterials;

    public FileMaterial(String name, String url, Date createdDate,
                                User createdBy, List<TrainingContent> trainingContents){
        this.name = name;
        this.url = url;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.trainingContents = trainingContents;
    }
}
