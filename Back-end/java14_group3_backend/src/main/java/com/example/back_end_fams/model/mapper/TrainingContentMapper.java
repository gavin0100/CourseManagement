package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.request.TrainingContentRequest;
import com.example.back_end_fams.model.request.TrainingUnitRequest;
import com.example.back_end_fams.model.response.TrainingContentResponse;
import com.example.back_end_fams.model.response.TrainingUnitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingContentMapper {
    @Mapping(source = "trainingContent.id", target = "id")
    @Mapping(source = "trainingContent.content", target = "content")
    @Mapping(source = "trainingContent.deliveryType", target = "deliveryType")
    @Mapping(source = "trainingContent.duration", target = "duration")
    @Mapping(source = "trainingContent.trainingFormat", target = "trainingFormat")
    @Mapping(source = "trainingContent.note", target = "note")
    TrainingContentResponse toResponse(TrainingContent trainingContent);

    List<TrainingContentResponse> toTrainingContentListDTO(List<TrainingContent> trainingContents);

    TrainingContent toEntity(TrainingContentRequest trainingContentRequest);
}
