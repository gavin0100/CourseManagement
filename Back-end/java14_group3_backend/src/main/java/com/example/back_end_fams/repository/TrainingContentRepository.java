package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.TrainingContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingContentRepository extends JpaRepository<TrainingContent, Integer> {
    @Query("SELECT c FROM TrainingContent c")
    public List<TrainingContent> findAllContent();

    public List<TrainingContent> findAllByTrainingUnit_UnitCode(int trainingUnit_unitCode);
}
