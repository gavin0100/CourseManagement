package com.example.back_end_fams.model.importRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SyllabusImport {
    private int topicCode;
    private String topicName;
    private String level;
    private String version;
    private String publishStatus;
    private int trainingAudience;
    private String technicalGroup;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String courseObjective;

    private String trainingPrinciples;
}
