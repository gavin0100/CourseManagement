package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.entity.ClassDate;
import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassDateRepository extends JpaRepository<ClassDate, Integer> {
    @Query("SELECT c FROM ClassDate c where c.class_room.classId = :classId")
    List<ClassDate> findByClassId(int classId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ClassDate c where c.class_room.classId = :classId")
    void deleteAllByClassId(int classId);


}
