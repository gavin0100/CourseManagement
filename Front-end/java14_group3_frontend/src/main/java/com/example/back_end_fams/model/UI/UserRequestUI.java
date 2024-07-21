package com.example.back_end_fams.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestUI {

    private int userId;
    private int userPermissionId;
    private String name;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dob;
    private String gender;
    private boolean status;
    private int modifiedById;
}
