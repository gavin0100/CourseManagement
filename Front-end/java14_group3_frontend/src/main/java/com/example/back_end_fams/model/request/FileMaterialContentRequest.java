package com.example.back_end_fams.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileMaterialContentRequest {
    private MultipartFile file;
    private int contentIdAddMaterial;
}
