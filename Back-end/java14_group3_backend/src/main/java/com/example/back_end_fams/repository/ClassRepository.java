package com.example.back_end_fams.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.back_end_fams.model.entity.Class;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer>, JpaSpecificationExecutor<Class> {

    List<Class> findByStatus(String status);
    List<Class> findByClassNameOrClassCode(String className, String classCode);
    Page<Class> findByStatus(String status, Pageable pageable);

    Page<Class> findByClassNameOrClassCode(Specification<Class> specification, String className, String classCode, Pageable pageable);


}
