package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.request.FileMaterialRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.model.response.FileMaterialResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMaterialMapper {
    @Mapping(source = "fileMaterial.fileId", target = "fileId")
    @Mapping(source = "fileMaterial.url", target = "url")
    @Mapping(source = "fileMaterial.name", target = "name")
    @Mapping(source = "fileMaterial.createdDate", target = "createdDate")
    @Mapping(source = "fileMaterial.createdBy", target = "createdBy")
    @Mapping(source = "fileMaterial.trainingContents", target = "trainingContents")
    @Mapping(source = "fileMaterial.trainingPrograms", target = "trainingPrograms")
    FileMaterialResponse toResponse(FileMaterial fileMaterial);

    List<FileMaterialResponse> toFileMaterialListDTO(List<FileMaterial> fileMaterials);

    FileMaterial toEntity(FileMaterialRequest fileMaterialRequest);
}
