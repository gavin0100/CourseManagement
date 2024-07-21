package com.example.back_end_fams.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LearningObjectiveRequest {
    private int code;
    private String name;
    private String type;
    private String description;
}
