package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.LearningObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningObjectiveRepository extends JpaRepository<LearningObjective,Integer> {
    List<LearningObjective> findLearningObjectiveByCode(int code);
    @Query("SELECT lo FROM LearningObjective lo")
    List<LearningObjective> findAllLearningObjective();
}
