package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.*;
import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.request.*;
import com.example.back_end_fams.model.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TrainingProgramMapper {
    @Mapping(source = "estimatedDuration", target = "duration")
    TrainingProgram toCreateEntity(TrainingProgramCreateRequest request);

    List<TrainingProgramResponse> toListResponse(List<TrainingProgram> list);
    @Mapping(source = "createdBy.name", target = "createdBy")
    TrainingProgramResponse toResponse(TrainingProgram trainingProgram);

    @Mapping(source = "classes", target = "trainingProgramClassDTOS")
    @Mapping(source = "trainingProgramSyllabuses", target = "trainingProgramSyllabusDTOS")
    @Mapping(source = "trainingMaterials", target = "trainingProgramFileMaterialDTOS")
    @Mapping(source = "modifiedBy.name", target = "modifiedBy")
    TrainingProgramDetailResponse toDetailResponse(TrainingProgram trainingProgram);
    TrainingProgramClassDTO toTrainingProgramClassDTOs (Class _class);
    @Mapping(source = "syllabus", target = "syllabusDTO")
    TrainingProgramSyllabusDTO toTrainingProgramSyllabusDTOs(TrainingProgramSyllabus trainingProgramSyllabus);
    @Mapping(source = "createdBy.name", target = "createdBy")
    TrainingProgramFileMaterialDTO toTrainingProgramFileMaterialDTOs(FileMaterial fileMaterial);
    List<TrainingProgramFileMaterialDTO> toListTrainingProgramFileMaterialDTOs(List<FileMaterial> fileMaterials);
    @Mapping(source = "createdBy.name", target = "createdBy")
    SyllabusDTO toSyllabusDTO(Syllabus syllabus);


    TrainingProgramDuplicateRequest toCloneTrainingProgramEntity(TrainingProgram trainingProgram);
    TrainingProgram toTrainingProgramEntity(TrainingProgramDuplicateRequest trainingProgramDuplicateRequest);
    Class toCloneClassEntity(Class class_);
    FileMaterial toCloneFileMaterialEntity(FileMaterial fileMaterial);
}
