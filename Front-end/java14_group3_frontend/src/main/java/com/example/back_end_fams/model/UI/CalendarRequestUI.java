package com.example.back_end_fams.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRequestUI {
    private String search;
    private String status;
    private String partOfDateMorning;
    private String partOfDateNoon;
    private String partOfDateNight;
    private String chosenDate;
    private String mode;
}
