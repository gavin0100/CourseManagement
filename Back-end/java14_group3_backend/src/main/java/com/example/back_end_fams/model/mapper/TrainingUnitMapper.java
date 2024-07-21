package com.example.back_end_fams.model.mapper;

import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.request.TrainingUnitRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.model.response.TrainingUnitResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingUnitMapper {
    @Mapping(source = "trainingUnit.unitCode", target = "unitCode")
    @Mapping(source = "trainingUnit.unitName", target = "unitName")
    @Mapping(source = "trainingUnit.unit", target = "unit")
    @Mapping(source = "trainingUnit.day", target = "day")
    @Mapping(source = "trainingUnit.numberOfHours", target = "numberOfHours")
    TrainingUnitResponse toResponse(TrainingUnit trainingUnit);

    List<TrainingUnitResponse> toTrainingUnitListDTO(List<TrainingUnit> trainingUnits);

    TrainingUnit toEntity(TrainingUnitRequest trainingUnitRequest);
}
