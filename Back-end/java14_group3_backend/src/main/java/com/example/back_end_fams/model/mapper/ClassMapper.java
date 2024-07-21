package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.response.ClassResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.back_end_fams.model.entity.Class;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    @Mapping(source = "classId", target = "classId")
    @Mapping(source = "trainingProgramCode", target = "trainingProgramCode")
    @Mapping(source = "className", target = "className")
    @Mapping(source = "classCode", target = "classCode")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "trainingProgram", target = "trainingProgram")
    ClassResponse toResponse(Class classRoom);

    List<ClassResponse> toClassDTO(List<Class> classRooms);

    Class toEntity(ClassRequest classRoom);
}
