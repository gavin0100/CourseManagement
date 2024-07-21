package com.example.back_end_fams.model.request;

import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    private String name;
    private String email;

}
