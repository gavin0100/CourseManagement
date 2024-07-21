package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramResponse {
    private int trainingProgramCode;
    private String name;

    private Date createdDate;

    private int duration;
    private String status;
    private String createdBy;

    private String generalInformation;
    private Date modifiedDate;
}
