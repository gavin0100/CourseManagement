package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.TrainingUnit;
import com.example.back_end_fams.model.response.TrainingUnitResponseV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingUnitRepository extends JpaRepository<TrainingUnit, Integer> {
    @Query(value = "SELECT * FROM training_unit WHERE topic_code = :topicCode", nativeQuery = true)
    List<TrainingUnit> findByTopicCode(int topicCode);

}


