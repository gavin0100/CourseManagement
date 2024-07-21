package com.example.back_end_fams.model.response;

import com.example.back_end_fams.model.entity.Syllabus;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUnitResponse implements Serializable {
    private int unitCode;
    private String unitName;
    private String day;
    private String unit;
    private int numberOfHours;
}
