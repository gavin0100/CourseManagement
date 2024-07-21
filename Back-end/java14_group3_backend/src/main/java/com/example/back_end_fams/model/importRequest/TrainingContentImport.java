package com.example.back_end_fams.model.importRequest;

import com.example.back_end_fams.model.entity.TrainingContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentImport {
    private int Id;
    private String content;
    public enum DeliveryTypeEnum {
        Assignment_Lab,
        Concept_Lecture,
        Guide_Review,
        Test_Quiz,
        Exam,
        Seminar_Workshop
    }
    private TrainingContent.DeliveryTypeEnum deliveryType;
    private int duration;
    private boolean trainingFormat;
    private String note;
    private int unit_code;
}
