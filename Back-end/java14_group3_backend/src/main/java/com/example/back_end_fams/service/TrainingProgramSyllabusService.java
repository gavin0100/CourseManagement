package com.example.back_end_fams.service;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.repository.TrainingProgramSyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingProgramSyllabusService {

    @Autowired
    private TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;

    public TrainingProgramSyllabus save(TrainingProgramSyllabus trainingProgramSyllabus){
        return trainingProgramSyllabusRepository.save(trainingProgramSyllabus);
    }

    public TrainingProgramSyllabus getByTrainingProgramAndSyllabus(int trainingProgramCode, int syllabusCode){
        return trainingProgramSyllabusRepository.findByTrainingProgramTrainingProgramCodeAndSyllabusTopicCode(trainingProgramCode, syllabusCode).orElse(null);
    }

    public List<TrainingProgramSyllabus> getTrainingProgramSyllabusByTrainingProgramCode(int trainingProgramCode){
        return trainingProgramSyllabusRepository.findByTrainingProgramTrainingProgramCode(trainingProgramCode).orElse(null);
    }

}
