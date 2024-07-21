package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TrainingProgramFileMaterialDTO {
    private int fileId;
    private String name;
    private String url;
    private String createdBy;
    private Date createdDate;
}
