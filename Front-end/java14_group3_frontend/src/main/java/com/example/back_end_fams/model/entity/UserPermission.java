package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

//@Setter
//@Getter
//@Entity
//@Table(name = "user_permission")
//@NoArgsConstructor
//@AllArgsConstructor
public class UserPermission implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "permission_id")
//    private int permissionId;
//    @NotEmpty(message = "role name is required!")
//    @Column(name = "role", length = 50)
//    private String role;
//    @NotEmpty(message = "syllabus permission is required!")
//    @Column(name = "syllabus", length = 50)
//    private String syllabus;
//    @NotEmpty(message = "training program permission is required!")
//    @Column(name = "training_program", length = 50)
//    private String trainingProgram;
//    @NotEmpty(message = "class permission is required!")
//    @Column(name = "class", length = 50)
//    private String class_room;
//    @NotEmpty(message = "learning material permission is required!")
//    @Column(name = "learning_material", length = 50)
//    private String learningMaterial;
//    @NotEmpty(message = "user management permission is required!")
//    @Column(name = "user_management", length = 50)
//    private String userManagement;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "userPermission", cascade = CascadeType.ALL)
//    private List<User> users;
}
