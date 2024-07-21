package com.example.back_end_fams.model.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SuccessMessage {
    private int statusCode;
    private String message;
    private String description;
    private Date timestamp;

}
