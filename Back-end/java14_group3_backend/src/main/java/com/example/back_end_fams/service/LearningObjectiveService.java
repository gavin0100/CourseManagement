package com.example.back_end_fams.service;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.repository.LearningObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
public class LearningObjectiveService {
    @Autowired
    private LearningObjectiveRepository learningObjectiveRepository;

    public LearningObjective saveObjective(LearningObjective learningObjective){
        return learningObjectiveRepository.save(learningObjective);
    }

    public List<LearningObjective> getAllObjective(){
        return learningObjectiveRepository.findAllLearningObjective();
    }
}
