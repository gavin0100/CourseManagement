package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.TrainingProgram;
import com.example.back_end_fams.model.response.TrainingProgramResponse;
import com.example.back_end_fams.model.response.TrainingProgramResponse2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingProgramMapper2 {
    @Mapping(source = "trainingProgramCode", target = "trainingProgramCode")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "duration", target = "duration")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdBy", target = "createdBy")
    TrainingProgramResponse2 toResponse(TrainingProgram trainingProgram);

//    List<TrainingProgramListResponse> toTrainingProgramListDTO(TrainingProgram trainingProgram);

}
