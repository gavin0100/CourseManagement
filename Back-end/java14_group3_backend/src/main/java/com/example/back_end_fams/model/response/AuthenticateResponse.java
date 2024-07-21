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
public class AuthenticateResponse implements Serializable {
    private String accessToken;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Date dob;

    private UserResponse createdBy;
    private Date createdDate;
}
