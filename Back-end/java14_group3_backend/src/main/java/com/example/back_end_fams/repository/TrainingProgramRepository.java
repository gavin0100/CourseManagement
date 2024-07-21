package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.TrainingProgram;
import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Integer>, JpaSpecificationExecutor<TrainingProgram> {

}
