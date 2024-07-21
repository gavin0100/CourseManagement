package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.Syllabus;
import com.example.back_end_fams.model.entity.TrainingContentFileMaterial;
import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingContentFileMaterialRepository  extends JpaRepository<TrainingContentFileMaterial, Integer> {
    Optional<List<TrainingContentFileMaterial>> findByTrainingContentId(int contentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TrainingContentFileMaterial f WHERE f.fileMaterial.fileId = :fileId AND f.trainingContent.Id = :contentId")
    void deleteByFileAndContent(@Param("fileId") int fileId, @Param("contentId") int contentId);

}
