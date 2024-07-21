package com.example.back_end_fams.model.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LearningObjectiveRequest {
    private int code;
    private String name;
    private String type;
    private String description;
}
