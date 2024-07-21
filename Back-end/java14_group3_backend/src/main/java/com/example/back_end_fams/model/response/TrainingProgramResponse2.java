package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class TrainingProgramResponse2 {
    private int trainingProgramCode;
    private String name;
    private Date createdDate;
    private int duration;
    private String status;
    private UserResponse createdBy;

    private UserResponse modifiedBy;
    private Date modifiedDate;
}
