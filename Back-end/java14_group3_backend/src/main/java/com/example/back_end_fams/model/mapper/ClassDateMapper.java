package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.entity.ClassDate;
import com.example.back_end_fams.model.request.ClassDateRequest;
import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.response.ClassDateResponse;
import com.example.back_end_fams.model.response.ClassResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassDateMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "partOfDate", target = "partOfDate")
    @Mapping(source = "day", target = "day")
    @Mapping(source = "class_room.classId", target = "class_id")
    @Mapping(source = "class_room", target = "classResponse")
    ClassDateResponse toResponse(ClassDate classDate);

    List<ClassDateResponse> toClassDateDTO(List<ClassDate> classDates);

    ClassDate toEntity(ClassDateRequest classDate);
}
