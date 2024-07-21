package com.example.back_end_fams.service;

import com.example.back_end_fams.model.entity.Syllabus;
import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.repository.TrainingContentRepository;
import com.example.back_end_fams.repository.TrainingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingContentService {
    @Autowired
    TrainingContentRepository trainingContentRepository;
    @Autowired
    TrainingUnitRepository trainingUnitRepository;

    public TrainingContent saveContent(TrainingContent trainingContent){
        return trainingContentRepository.save(trainingContent);
    }


    public TrainingContent getContentById(int id){
        return trainingContentRepository.findById(id).orElse(null);
    }
    public TrainingContent editContent(TrainingContent trainingContent){
        return trainingContentRepository.save(trainingContent);
    }
    public List<TrainingContent> getContentByUnitCode(int id){
        TrainingUnit trainingUnit = trainingUnitRepository.findById(id).orElse(null);

        if (trainingUnit != null) {
            return new ArrayList<>(trainingUnit.getTrainingContents());
        } else {
            // Xử lý khi không tìm thấy Syllabus theo syllabusId
            return Collections.emptyList();
        }
    }
    public List<TrainingContent> getAll(){
        return trainingContentRepository.findAllContent();
    }

}
