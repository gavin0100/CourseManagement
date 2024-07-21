package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.model.request.LearningObjectiveRequest;
import com.example.back_end_fams.model.response.LearningObjectiveResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearningObjectiveMapper {
    @Mapping(source = "learning_objective.code", target = "code")
    @Mapping(source = "learning_objective.type", target = "type")
    @Mapping(source = "learning_objective.name", target = "name")
    @Mapping(source = "learning_objective.description", target = "description")
    LearningObjectiveResponse toResponse(LearningObjective learning_objective);

    List<LearningObjectiveResponse> toLearningObjectiveListDTO(List<LearningObjective> learning_objective);

    LearningObjective toEntity(LearningObjectiveRequest learning_objectiveRequest);
}
