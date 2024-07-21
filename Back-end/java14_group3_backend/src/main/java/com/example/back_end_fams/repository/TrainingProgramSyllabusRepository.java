package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingProgramSyllabusRepository extends JpaRepository<TrainingProgramSyllabus,Integer> {

    Optional<TrainingProgramSyllabus>findByTrainingProgramTrainingProgramCodeAndSyllabusTopicCode(int trainingProgramCode, int syllabusCode);

    Optional<List<TrainingProgramSyllabus>>findByTrainingProgramTrainingProgramCode(int trainingProgramCode);
    Optional<TrainingProgramSyllabus> findAllByTrainingProgram_TrainingProgramCodeAndSyllabus_TopicCode(int trainingProgram_trainingProgramCode, int syllabus_topicCode);
    List<TrainingProgramSyllabus> findAllByTrainingProgram_TrainingProgramCode(int trainingProgram_trainingProgramCode);
    Optional<TrainingProgramSyllabus> findTrainingProgramSyllabusBySyllabus_TopicCode(int syllabus_topicCode);
}
