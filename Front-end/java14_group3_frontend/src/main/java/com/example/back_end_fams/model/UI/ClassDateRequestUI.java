package com.example.back_end_fams.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDateRequestUI {
    private String listOfDate;
    private String partOfDate;
    private int classId;
}
