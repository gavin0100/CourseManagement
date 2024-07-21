package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.*;
import com.example.back_end_fams.model.request.SyllabusRequest;
import com.example.back_end_fams.model.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyllabusMapper {
    @Mapping(source = "syllabus.topicCode", target = "topicCode")
    @Mapping(source = "syllabus.createdBy", target = "createdBy")
    @Mapping(source = "syllabus.modifiedBy", target = "modifiedBy")
    @Mapping(source = "syllabus.trainingAudience", target = "trainingAudience")
    @Mapping(source = "syllabus.createdDate", target = "createdDate")
    @Mapping(source = "syllabus.modifiedDate", target = "modifiedDate")
    @Mapping(source = "syllabus.publishStatus", target = "publishStatus")
    @Mapping(source = "syllabus.topicName", target = "topicName")
    @Mapping(source = "syllabus.trainingPrinciples", target = "trainingPrinciples")
    @Mapping(source = "syllabus.version", target = "version")
    @Mapping(source = "syllabus.technicalGroup", target = "technicalGroup")
    @Mapping(source = "syllabus.level", target = "level")
    @Mapping(source = "syllabus.topicOutline", target = "topicOutline")
    @Mapping(source = "syllabus.learningObjectives", target = "learningObjectives")
    @Mapping(source = "syllabus.courseObjective", target = "courseObjective")
    SyllabusResponse toResponse(Syllabus syllabus);

    List<SyllabusResponse> toSyllabusListDTO(List<Syllabus> syllabus);

    Syllabus toEntity(SyllabusRequest syllabusRequest);

    @Mapping(source = "topicOutline", target = "topicOutline")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    SyllabusResponseV2 toSyllabusDetailResponse(Syllabus syllabus);

    @Mapping(source = "trainingContents", target = "trainingContents")
    TrainingUnitResponseV2 toTrainingUnitResponse (TrainingUnit trainingUnit);

    @Mapping(source = "learningObjective", target = "learningObjective")
    TrainingContentResponse toTrainingContentResponse(TrainingContent trainingContent);

    LearningObjectiveResponse toLearningObjectiveResponse (LearningObjective learningObjective);

    UserResponse toUserResponse(User user);
}
