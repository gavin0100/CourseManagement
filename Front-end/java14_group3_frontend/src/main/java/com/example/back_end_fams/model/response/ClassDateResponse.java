package com.example.back_end_fams.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDateResponse implements Serializable {
    private int id;
    private String partOfDate;
    private Date day;
    private int class_id;

    private ClassResponse classResponse;
}
