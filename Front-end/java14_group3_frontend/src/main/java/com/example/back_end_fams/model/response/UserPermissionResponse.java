package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionResponse implements Serializable {
    private int permissionId;
    private String role;
    private String syllabus;
    private String trainingProgram;
    private String classRoom;
    private String learningMaterial;
    private String userManagement;
}
