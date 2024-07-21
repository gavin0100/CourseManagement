package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.UserPermission;
import com.example.back_end_fams.model.request.UserPermissionRequest;
import com.example.back_end_fams.model.response.UserPermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPermissionMapper {
    @Mapping(source = "userPermission.permissionId", target = "permissionId")
    @Mapping(source = "userPermission.role", target = "role")
    @Mapping(source = "userPermission.syllabus", target = "syllabus")
    @Mapping(source = "userPermission.trainingProgram", target = "trainingProgram")
    @Mapping(source = "userPermission.classRoom", target = "classRoom")
    @Mapping(source = "userPermission.learningMaterial", target = "learningMaterial")
    @Mapping(source = "userPermission.userManagement", target = "userManagement")
    UserPermissionResponse toResponse(UserPermission userPermission);

    List<UserPermissionResponse> toUserPermissionListDTO(List<UserPermission> userPermissions);

    UserPermission toEntity(UserPermissionRequest userPermissionRequest);
    List<UserPermission> toUserPermissionListEntity(List<UserPermissionRequest> userPermissionRequest);

}
