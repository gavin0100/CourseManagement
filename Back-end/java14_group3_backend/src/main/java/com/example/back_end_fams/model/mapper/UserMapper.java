package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.dob", target = "dob")
    @Mapping(source = "user.userPermission", target = "userPermissionResponse")
    @Mapping(source = "user.status", target = "status")
    @Mapping(source = "user.createdBy", target = "createdBy")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.modifiedBy", target = "modifiedBy")
    @Mapping(source = "user.modifiedDate", target = "modifiedDate")
    @Mapping(source = "user.password", target = "password")
    @Mapping(source = "userPermission.permissionId", target = "userPermissionId")
    @Mapping(source = "createdBy.userId", target = "createdByUserId")
    @Mapping(source = "modifiedBy.userId", target = "modifiedByUserId")
    UserResponse toResponse(User user);

    List<UserResponse> toUserListDTO(List<User> users);

    User toEntity(UserRequest userRequest);

}
