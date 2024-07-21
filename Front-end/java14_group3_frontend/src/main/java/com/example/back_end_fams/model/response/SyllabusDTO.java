package com.example.back_end_fams.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class SyllabusDTO {
    private String topicCode;
    private String topicName;
    private String version;
    private String publishStatus;
    private Integer duration;
    private String createdBy;
    private Date modifiedDate;
    private String level;

}
