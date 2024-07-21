package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.model.response.TrainingProgramSyllabusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingProgramSyllabusMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "sequence", target = "sequence")
    @Mapping(source = "trainingProgram", target = "trainingProgram")
    @Mapping(source = "syllabus", target = "syllabus")
    TrainingProgramSyllabusResponse toResponse(TrainingProgramSyllabus trainingProgramSyllabus);

    List<TrainingProgramSyllabusResponse> toTrainingProgramSyllabusDTO(List<TrainingProgramSyllabus> trainingProgramSyllabus);

//    TrainingProgramSyllabus toEntity(TrainingProgramSyllabusResponse trainingProgramSyllabus);
}
