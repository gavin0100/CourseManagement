package com.example.back_end_fams.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class TrainingProgramFileMaterialDTO {
    private int fileId;
    private String name;
    private String url;
    private String createdBy;
    private Date createdDate;
}
