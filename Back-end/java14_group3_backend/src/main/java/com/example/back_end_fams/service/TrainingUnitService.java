package com.example.back_end_fams.service;

import com.example.back_end_fams.model.entity.Syllabus;
import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.mapper.TrainingContentMapper;
import com.example.back_end_fams.model.response.TrainingContentResponse;
import com.example.back_end_fams.model.response.TrainingUnitResponseV2;
import com.example.back_end_fams.repository.SyllabusRepository;
import com.example.back_end_fams.repository.TrainingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingUnitService {
    @Autowired
    TrainingUnitRepository trainingUnitRepository;
    @Autowired
    SyllabusRepository syllabusRepository;
    @Autowired
    TrainingContentMapper trainingContentMapper;
    @Autowired
    TrainingContentService trainingContentService;
    public TrainingUnit saveTrainingUnit(TrainingUnit trainingUnit){
        return trainingUnitRepository.save(trainingUnit);
    }
    public TrainingUnit getTrainUnitById(int id){
        return trainingUnitRepository.findById(id).orElse(null);
    }

    public List<TrainingUnit> getTrainingUnitsBySyllabusId(int syllabusId) {
        Syllabus syllabus = syllabusRepository.findById(syllabusId).orElse(null);
        if (syllabus != null) {
            return new ArrayList<>(syllabus.getTopicOutline());
        } else {
            // Xử lý khi không tìm thấy Syllabus theo syllabusId
            return Collections.emptyList();
        }
    }

    public TrainingUnit editUnit(TrainingUnit trainingUnit){
        return trainingUnitRepository.save(trainingUnit);
    }
}
