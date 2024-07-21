package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMaterialRepository extends JpaRepository<FileMaterial, Integer> {
    Optional<FileMaterial> findAllByName(String name);
    @Query("SELECT file FROM FileMaterial file INNER JOIN file.trainingPrograms tr INNER JOIN tr.trainingMaterials tr1 ON tr1.fileId = file.fileId AND tr.trainingProgramCode = :trainingProgramCode AND file.fileId = :fileId")
    FileMaterial getFileMaterial(@Param("fileId") Integer fileId, @Param("trainingProgramCode") Integer trainingProgramCode);

}
